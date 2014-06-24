package net.therap.service.common;

import net.therap.db.entity.ar.ArClient;
import net.therap.db.entity.common.Client;
import net.therap.db.entity.common.ClientDetail;
import net.therap.db.entity.common.Country;
import net.therap.db.entity.common.Provider;
import net.therap.db.entity.medicalInfo.DiagnosisCode;
import net.therap.db.entity.medicalInfo.IndividualDiagnosis;
import net.therap.model.ar.*;
import net.therap.model.therap.DiagnosisMap;
import net.therap.model.therap.MigrationDataUnit;
import net.therap.service.ar.ArDataService;
import net.therap.service.therap.TherapDataService;
import net.therap.util.CollectionUtils;
import net.therap.util.StopWatch;
import net.therap.util.StringUtils;
import net.therap.util.TherapDomainFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ashraf
 * @since 5/20/14
 */
@Service
public class ArIdfMigrationService {

    private static final int MAX_ROWS = 25000;

    private static final int DEFAULT_NOW_YEAR = 2014;
    private static final int DEFAULT_NOW_MONTH = 7;
    private static final int DEFAULT_NOW_DAY = 1;

    private static final int TYPE_EC_MIN_AGE = 3;
    private static final int TYPE_EC_MAX_AGE = 5;

    private static List<String> VALID_CMS_MASTER_STATUS = Arrays.asList("A", "J", "K", "P", "X");

    private static final Logger log = LoggerFactory.getLogger(ArIdfMigrationService.class);

    @Autowired
    private Provider activeProvider;

    @Autowired
    private Country activeCountry;

    @Autowired
    private ArDataService arDataService;

    @Autowired
    private TherapDataService therapDataService;

    public void processMigration() {

        //filterDdsRoot();
        //filterCmsRoot();

        log.info("Active Country : " + activeCountry.getName());
//        migrateForDDSRoot();
        migrateForCMSMaster();
    }

    public void filterDdsRoot() {
        long rowCount = arDataService.getDdsRootCount(false);
        int firstResult = 0;

        while (firstResult <= rowCount) {
            List<DdsRoot> ddsRoots = arDataService.getDdsRoots(firstResult, MAX_ROWS, false);

            if (CollectionUtils.isNotEmpty(ddsRoots)) {

                List<Long> invalidRootIds = new ArrayList<Long>();
                for (DdsRoot root : ddsRoots) {
                    if (!isValidDdsRoot(root)) {
                        invalidRootIds.add(root.getClientId());
                        log.debug("[SET ROW INVALID] DDS ID = {}", root.getClientId());
                    }
                }

                if (CollectionUtils.isNotEmpty(invalidRootIds)) {
                    arDataService.updateInvalidDdsRoot(invalidRootIds);
                }
            }

            firstResult = firstResult + MAX_ROWS;
        }
    }

    public void filterCmsRoot() {
        long rowCount = arDataService.getCmsMasterCount(false);
        int firstResult = 0;

        while (firstResult <= rowCount) {
            List<CmsMaster> cmsMasters = arDataService.getCmsMasters(firstResult, MAX_ROWS, false);

            if (CollectionUtils.isNotEmpty(cmsMasters)) {

                List<Long> invalidMasterIds = new ArrayList<Long>();

                for (CmsMaster master : cmsMasters) {
                    if (!isValidCmsMaster(master)) {
                        invalidMasterIds.add(master.getCmsId());
                        log.debug("[SET ROW INVALID] CMS ID = {}", master.getCmsId());
                    }
                }

                if (CollectionUtils.isNotEmpty(invalidMasterIds)) {
                    arDataService.updateInvalidCmsMaster(invalidMasterIds);
                }
            }

            firstResult = firstResult + MAX_ROWS;
        }
    }

    public void migrateForDDSRoot() {
        long rowCount = arDataService.getDdsRootCount(true);
        int firstResult = 0;
        int processedRow = 0;

        while (firstResult <= rowCount) {
            List<DdsRoot> ddsRoots = arDataService.getDdsRoots(firstResult, MAX_ROWS, true);

            if (CollectionUtils.isNotEmpty(ddsRoots)) {
                for (DdsRoot root : ddsRoots) {

                    CmsMaster cmsMaster = null;
                    try {

                        long clientId = root.getClientId();
                        cmsMaster = arDataService.getCmsMasterByClientId(clientId);

                        processedRow++;
                        migrateToTherapIdf(cmsMaster, root, clientId);
                        log.debug("[SUCCESS] - " + "Processed File :" + "DDS ROOT" + getClientInfo(root, cmsMaster));
                    } catch (ConstraintViolationException e) {
                        log.debug("[FAILURE] - " + "Processed File :" + "DDS ROOT" + getClientInfo(root, cmsMaster) + "CAUSE : " + "DUPLICATE CLIENT");
                        log.debug(e.toString(), e);
                    } catch (Exception e) {
                        log.debug("[FAILURE] - " + "Processed File :" + "DDS ROOT" + getClientInfo(root, cmsMaster) + "CAUSE : " + e.toString());
                        log.debug(e.toString(), e);
                    }
                }
            }

            firstResult = firstResult + MAX_ROWS;
        }
        log.debug("[PROCESSED ROW DDS] " + processedRow);
    }

    public void migrateForCMSMaster() {
        long rowCount = arDataService.getCmsMasterCount(true);
        int firstResult = 0;
        int processedRow = 0;

        while (firstResult <= rowCount) {
            List<CmsMaster> cmsMasters = arDataService.getCmsMasters(firstResult, MAX_ROWS, true);

            if (CollectionUtils.isNotEmpty(cmsMasters)) {
                for (CmsMaster master : cmsMasters) {
                    DdsRoot ddsRoot = null;
                    try {

                        Long clientId = master.getCmsPcpExemptDate();
                        ddsRoot = arDataService.getDdsRootByClientId(clientId);

//                        if (ddsRoot != null && therapDataService.getArClient(ddsRoot.getClientId(), master.getCmsId(), activeProvider.getId()) != null) {
//                            log.debug("[SKIPPED] CMS row already processed from linked DDS Row" + getClientInfo(ddsRoot, master));
//                            continue;
//                        }

                        processedRow++;
                        migrateToTherapIdf(master, ddsRoot, clientId);
                        log.debug("[SUCCESS] - " + "Processed File :" + "CMS MASTER" + getClientInfo(ddsRoot, master));
                    } catch (ConstraintViolationException e) {
                        log.debug("[FAILURE] - " + "Processed File :" + "CMS MASTER" + getClientInfo(ddsRoot, master) + "CAUSE : " + "DUPLICATE CLIENT");
                        log.debug(e.toString(), e);
                    } catch (Exception e) {
                        log.debug("[FAILURE] - " + "Processed File :" + "CMS MASTER" + getClientInfo(ddsRoot, master) + "CAUSE : " + e.toString());
                        log.debug(e.toString(), e);
                    }
                }
            }

            firstResult = firstResult + MAX_ROWS;
        }

        log.debug("[PROCESSED ROW CMS] " + processedRow);
    }

    public void migrateToTherapIdf(CmsMaster master, DdsRoot ddsRoot, Long clientId) {

        StopWatch stopWatch = StopWatch.start();
        DdsCmFinance finance = arDataService.getDdsCmFinanceByClientId(clientId);

        log.debug("[Elapsed time] : Fetch DDS CM Finance : " + stopWatch.elapsed());

        List<MedicaidDenial> denials = arDataService.getMedicaidDenialsByClientId(clientId);

        log.debug("[Elapsed time] : Fetch Medical Denial : " + stopWatch.elapsedLast());

        DdsField field = arDataService.getDdsFieldByClientId(clientId);

        log.debug("[Elapsed time] : Fetch DDS Field : " + stopWatch.elapsedLast());

        DdsFinancial financial = arDataService.getDdsFinancialByClientId(clientId);

        log.debug("[Elapsed time] : Fetch DDS Financial : " + stopWatch.elapsedLast());

        TherapDomainFactory therapDomainFactory = new TherapDomainFactory(master, ddsRoot, field, denials, financial, finance);

        Client client = therapDomainFactory.createClient(activeProvider);
        ClientDetail clientDetail = therapDomainFactory.createClientDetail(activeCountry);
        ArClient arClient = therapDomainFactory.createArClient(activeProvider);

        log.debug("[Elapsed time] : Create Client, ClientDetail and ArClient : " + stopWatch.elapsedLast());

        List<IndividualDiagnosis> individualDiagnoses = getIndividualDiagnosisList(ddsRoot, master);

        log.debug("[Elapsed time] : Fetch Individual Diagnosis : " + stopWatch.elapsedLast());

        MigrationDataUnit migrationDataUnit = new MigrationDataUnit(client, clientDetail, arClient, individualDiagnoses);

        therapDataService.saveTherapIdf(migrationDataUnit);

        log.debug("[Elapsed time] : Save Therap IDF : " + stopWatch.elapsedLast());

        log.debug("[Elapsed time] Total time Migrate to Therap IDF : " + stopWatch.elapsed());

        log.debug("[SAVED THERAP DATA] :" + migrationDataUnit.getSavedEntityInfo());
    }

    public void migrateToTherapIdfWithMergedDds(CmsMaster master, DdsRoot ddsRoot, Long clientId) {

        StopWatch stopWatch = StopWatch.start();

        MergedDdsChild mergedDdsChild = arDataService.getMergedDdsChildByClientId(clientId);

        log.debug("[Elapsed time] : Fetch Merged DDS Info : " + stopWatch.elapsed());

        List<MedicaidDenial> denials = arDataService.getMedicaidDenialsByClientId(clientId);

        log.debug("[Elapsed time] : Fetch Medical Denial : " + stopWatch.elapsedLast());

        TherapDomainFactory therapDomainFactory = new TherapDomainFactory(master, ddsRoot, mergedDdsChild, denials);

        Client client = therapDomainFactory.createClient(activeProvider);
        ClientDetail clientDetail = therapDomainFactory.createClientDetail(activeCountry);
        ArClient arClient = therapDomainFactory.createArClient(activeProvider);

        log.debug("[Elapsed time] : Create Client, ClientDetail and ArClient : " + stopWatch.elapsedLast());

        List<IndividualDiagnosis> individualDiagnoses = getIndividualDiagnosisList(ddsRoot, master);

        log.debug("[Elapsed time] : Fetch Individual Diagnosis : " + stopWatch.elapsedLast());

        MigrationDataUnit migrationDataUnit = new MigrationDataUnit(client, clientDetail, arClient, individualDiagnoses);

        therapDataService.saveTherapIdf(migrationDataUnit);

        log.debug("[Elapsed time] : Save Therap IDF : " + stopWatch.elapsedLast());

        log.debug("[Elapsed time] Total time Migrate to Therap IDF : " + stopWatch.elapsed());

        log.debug("[SAVED THERAP DATA] :" + migrationDataUnit.getSavedEntityInfo());
    }

    public List<IndividualDiagnosis> getIndividualDiagnosisList(DdsRoot ddsRoot, CmsMaster master) {

        if (master == null) {
            return null;
        }
        List<IndividualDiagnosis> individualDiagnoses = new ArrayList<IndividualDiagnosis>();
        List<String> diagnosisCodeList = Arrays.asList(master.getCmsDiagnosis1(), master.getCmsDiagnosis2(),
                master.getCmsDiagnosis3(), master.getCmsDiagnosis4(), master.getCmsDiagnosis5(),
                master.getCmsDiagnosis6());

        for (String code : diagnosisCodeList) {
            if (StringUtils.isEmpty(code)) {
                continue;
            }

            code = formatDiagnosisCode(code);

            if (DiagnosisMap.invalidDiagnosisMap.containsKey(code)) {
                code = DiagnosisMap.invalidDiagnosisMap.get(code);
            }

            DiagnosisCode diagnosis = therapDataService.getIndividualDiagnosis(code);

            if (diagnosis != null) {
                IndividualDiagnosis individualDiagnosis = new IndividualDiagnosis();
                individualDiagnosis.setDiagnosisCode(diagnosis);
                individualDiagnosis.setTz("US/Central");
                individualDiagnoses.add(individualDiagnosis);
            } else {
                log.info("[ERROR:MAPPING_NOT_FOUND] Diagnosis= " + code + " " + getClientInfo(ddsRoot, master));
            }

        }

        return individualDiagnoses;
    }

    private String formatDiagnosisCode(String code) {

        code = code.replaceAll("\\W+", "");
        int indexOfDot = code.startsWith("E") ? 4 : 3;

        if (code.length() > indexOfDot) {
            code = new StringBuffer(code).insert(indexOfDot, ".").toString();
        }

        return code;
    }

    private boolean isValidCmsMaster(CmsMaster master) {

        String status = master.getCmsStatus();

        for (String validStatus : VALID_CMS_MASTER_STATUS) {
            if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase(validStatus)) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidDdsRoot(DdsRoot root) {

        DateTime now = new DateTime(DEFAULT_NOW_YEAR, DEFAULT_NOW_MONTH, DEFAULT_NOW_DAY, 0, 0, 0, 0);

        Period period = new Period(now, new DateTime(root.getClientDateOfBirth()));
        int age = period.getYears();

        String ifspType = arDataService.getDdsIfspTypeByClientId(root.getClientId());
        boolean isDdsRootTypeEc = (age >= TYPE_EC_MIN_AGE && age <= TYPE_EC_MAX_AGE)
                && StringUtils.isNotEmpty(ifspType) && ifspType.equalsIgnoreCase("EC");

        if (isDdsRootTypeEc) {
            return true;
        } else {

            boolean isDdsRootTypeEi = arDataService.isActiveEiClientExists(root.getClientId());
            boolean isDdsRootTypeDdtcs = (!isDdsRootTypeEi && age < TYPE_EC_MIN_AGE);

            return isDdsRootTypeEi || isDdsRootTypeDdtcs;
        }
    }

    public String getClientInfo(DdsRoot ddsRoot, CmsMaster master) {

        String name = null, ssnNumber = null;
        Long ddsId = null, cmsId = null;
        Date dateOfBirth = null;

        if (ddsRoot != null) {

            ssnNumber = ddsRoot.getClientSsn() != null ? String.valueOf(ddsRoot.getClientSsn()) : null;
            dateOfBirth = ddsRoot.getClientDateOfBirth();
            name = StringUtils.join(ddsRoot.getClientLastname(), ddsRoot.getClientFirstname(), ddsRoot.getClientMiddlename());
            ddsId = ddsRoot.getClientId();
        }

        if (master != null) {

            ssnNumber = master.getCmsSsn() != null ? String.valueOf(master.getCmsSsn()) : null;
            dateOfBirth = master.getCmsDob();
            name = master.getCmsName();
            cmsId = master.getCmsId();
        }

        return " Client Name : " + name +
                " DDS ID :" + ddsId +
                " CMS ID :" + cmsId +
                " SSN Number :" + ssnNumber +
                " Date of Birth :" + dateOfBirth;
    }

}

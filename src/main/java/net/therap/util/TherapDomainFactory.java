package net.therap.util;

import net.therap.config.FormIdGenerator;
import net.therap.config.FormIdGeneratorBean;
import net.therap.db.entity.ar.*;
import net.therap.db.entity.common.*;
import net.therap.db.util.CommonForm;
import net.therap.db.util.Globals;
import net.therap.model.ar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author ashraf
 * @since 5/20/14
 */
public class TherapDomainFactory {

    private CmsMaster cmsMaster;
    private DdsRoot ddsRoot;
    private DdsField ddsField;
    private List<MedicaidDenial> medicaidDenialList;
    private DdsFinancial ddsFinancial;
    private DdsCmFinance ddsCmFinance;
    private MergedDdsChild mergedDdsChild;

    private static Logger logger = LoggerFactory.getLogger(TherapDomainFactory.class);
    private static Map<String, Integer> cmsMasterClientRaceMap = new HashMap<String, Integer>();
    private static Map<String, Integer> ddsRootClientRaceMap = new HashMap<String, Integer>();
    private static Map<Integer, String> cmsMasterGenderMap = new HashMap<Integer, String>();
    private static Map<String, String> ddsRootGenderMap = new HashMap<String, String>();
    private static Map<String, Integer> cmsMasterClientStatusMap = new HashMap<String, Integer>();
    private static Map<String, Integer> cmsMasterValidClientStatusMap = new HashMap<String, Integer>();
    private static Map<String, Integer> cmsMasterClientEthnicityMap = new HashMap<String, Integer>();

    static {
        ddsRootClientRaceMap.put("0", 9999);
        ddsRootClientRaceMap.put("1", 4);
        ddsRootClientRaceMap.put("2", 2);
        ddsRootClientRaceMap.put("3", 11);
        ddsRootClientRaceMap.put("4", 3);
        ddsRootClientRaceMap.put("5", 9999);
        ddsRootClientRaceMap.put("6", 9999);
        ddsRootClientRaceMap.put("7", 1);
        ddsRootClientRaceMap.put("8", 9999);
        ddsRootClientRaceMap.put("9", 15);

        cmsMasterClientRaceMap.put("8", 4);
        cmsMasterClientRaceMap.put("2", 2);
        cmsMasterClientRaceMap.put("3", 3);
        cmsMasterClientRaceMap.put("6", 9999);
        cmsMasterClientRaceMap.put("1", 1);
        cmsMasterClientRaceMap.put("4", 9999);
        cmsMasterClientRaceMap.put("5", 15);
        cmsMasterClientRaceMap.put("0", 9999);
        cmsMasterClientRaceMap.put("7", 9999);
        cmsMasterClientRaceMap.put("9", 9999);

        cmsMasterClientEthnicityMap.put("9C", 2);
        cmsMasterClientEthnicityMap.put("9c", 2);
        cmsMasterClientEthnicityMap.put("6", 4);

        ddsRootGenderMap.put("M", "M");
        ddsRootGenderMap.put("F", "F");

        cmsMasterGenderMap.put(0, null);
        cmsMasterGenderMap.put(1, "M");
        cmsMasterGenderMap.put(2, "F");
        cmsMasterGenderMap.put(3, "U");

        cmsMasterClientStatusMap.put("A", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterClientStatusMap.put("C", Globals.CLIENT_STATUS_DISCHARGED);
        cmsMasterClientStatusMap.put("D", Globals.CLIENT_STATUS_DISCHARGED);
        cmsMasterClientStatusMap.put("J", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterClientStatusMap.put("K", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterClientStatusMap.put("M", Globals.CLIENT_STATUS_DISCHARGED);
        cmsMasterClientStatusMap.put("P", Globals.CLIENT_STATUS_PENDING);
        cmsMasterClientStatusMap.put("X", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterClientStatusMap.put("W", Globals.CLIENT_STATUS_DISCHARGED);

        cmsMasterValidClientStatusMap.put("A", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterValidClientStatusMap.put("J", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterValidClientStatusMap.put("K", Globals.CLIENT_STATUS_ADMITTED);
        cmsMasterValidClientStatusMap.put("P", Globals.CLIENT_STATUS_PENDING);
        cmsMasterValidClientStatusMap.put("X", Globals.CLIENT_STATUS_ADMITTED);
    }

    public TherapDomainFactory(CmsMaster cmsMaster, DdsRoot ddsRoot, DdsField ddsField, List<MedicaidDenial> medicaidDenialList, DdsFinancial ddsFinancial, DdsCmFinance ddsCmFinance) {
        this.cmsMaster = cmsMaster;
        this.ddsRoot = ddsRoot;
        this.ddsField = ddsField;
        this.medicaidDenialList = medicaidDenialList;
        this.ddsFinancial = ddsFinancial;
        this.ddsCmFinance = ddsCmFinance;
    }

    public TherapDomainFactory(CmsMaster cmsMaster, DdsRoot ddsRoot, MergedDdsChild mergedDdsChild, List<MedicaidDenial> medicaidDenialList) {
        this.cmsMaster = cmsMaster;
        this.ddsRoot = ddsRoot;
        this.mergedDdsChild = mergedDdsChild;
        this.medicaidDenialList = medicaidDenialList;
    }

    public Client createClient(Provider provider) {
        Client client = new Client();
        FormIdGenerator formIdGenerator = new FormIdGeneratorBean();
        client.setFormId(formIdGenerator.generate(provider, CommonForm.FT_IDF, false));
        String ssn, medicaidNumber;

        if (ddsRoot != null) {
            client.setClientAdmissionDate(ddsRoot.getClientDateOfApplication());

            medicaidNumber = (ddsRoot.getClientMedicaidNo() != null && ddsRoot.getClientMedicaidNo() != 0) ? String.valueOf(ddsRoot.getClientMedicaidNo()) : null;
            client.setMedicaidNumber(medicaidNumber);

            ssn = (ddsRoot.getClientSsn() != null && ddsRoot.getClientSsn() != 0) ? String.valueOf(ddsRoot.getClientSsn()) : null;
            client.setSsn(ssn);

            client.setBirthDate(ddsRoot.getClientDateOfBirth());
            client.setGender(ddsRoot.getClientSex() != null ? ddsRootGenderMap.get(ddsRoot.getClientSex()) : null);
            client.setStatus(Globals.CLIENT_STATUS_ADMITTED);

            setClientNameByDdsRoot(client);
        }

        if (cmsMaster != null) {
            medicaidNumber = (cmsMaster.getCmsMedicaidNo() != null && cmsMaster.getCmsMedicaidNo() != 0) ?
                    String.valueOf(cmsMaster.getCmsMedicaidNo()) : client.getMedicaidNumber();
            client.setMedicaidNumber(medicaidNumber);

            ssn = (cmsMaster.getCmsSsn() != null && cmsMaster.getCmsSsn() != 0) ? String.valueOf(cmsMaster.getCmsSsn()) : client.getSsn();
            client.setSsn(ssn);

            client.setBirthDate(cmsMaster.getCmsDob() != null ? cmsMaster.getCmsDob() : client.getBirthDate());
            client.setGender(cmsMaster.getCmsSex() != null ? cmsMasterGenderMap.get(cmsMaster.getCmsSex()) : client.getGender());
            if (StringUtils.isNotEmpty(cmsMaster.getCmsStatus())) {
                if (cmsMasterClientStatusMap.keySet().contains(cmsMaster.getCmsStatus())) {
                    if (cmsMasterValidClientStatusMap.keySet().contains(cmsMaster.getCmsStatus())) {
                        client.setStatus(cmsMasterClientStatusMap.get(cmsMaster.getCmsStatus()));
                    }
                } else {
                    logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Status for Client= " + cmsMaster.getCmsStatus());
                }

            } else if (client.getStatus() == 0) {
                client.setStatus(Globals.CLIENT_STATUS_PENDING);
            }
            setClientNameByCmsMaster(client);
        }

        client.setTz("US/Central");
        client.setProvider(provider);
//        createArClientOversight(client, provider, oversightId);
        return client;
    }

    public void setClientNameByCmsMaster(Client client) {
        String[] names = cmsMaster.getCmsName().split("\\s");

        if (names.length < 3) {
            client.setLastName(names[0]);
            client.setFirstName(names.length > 1 ? names[1] : null);
            client.setMiddleName(null);
        } else if (names.length == 3) {
            client.setLastName(names[0]);
            client.setFirstName(names[1]);
            client.setMiddleName(names[2]);
        } else if (names.length > 3) {
            client.setLastName(StringUtils.joinWithDelimiter(" ", Arrays.copyOfRange(names, 0, 2)));
            client.setFirstName(StringUtils.joinWithDelimiter(" ", Arrays.copyOfRange(names, 2, names.length)));
            client.setMiddleName(null);
        }
    }


    public void setClientNameByDdsRoot(Client client) {
        client.setFirstName(ddsRoot.getClientFirstname());
        client.setMiddleName(ddsRoot.getClientMiddlename());
        client.setLastName(ddsRoot.getClientLastname());
    }

    public ClientDetail createClientDetail(Country country) {

        ClientDetail clientDetail = new ClientDetail();
        String mailZip, mailZip2, resZip, resZip2;

        if (cmsMaster != null) {
            clientDetail.setMailingStreet1(cmsMaster.getCmsMailAddress());
            clientDetail.setMailingCity(cmsMaster.getCmsMailCity());
            clientDetail.setMailingState(cmsMaster.getCmsMailState());

            mailZip = (cmsMaster.getCmsMailZip() != null && cmsMaster.getCmsMailZip() != 0) ? String.valueOf(cmsMaster.getCmsMailZip()) : null;
            mailZip2 = (cmsMaster.getCmsMailZip2() != null && cmsMaster.getCmsMailZip2() != 0) ? String.valueOf(cmsMaster.getCmsMailZip2()) : null;

//            if (StringUtils.isNotEmpty(mailZip) && StringUtils.isNotEmpty(mailZip2)) {
            clientDetail.setMailingZip(StringUtils.joinWithDelimiter("-", mailZip, mailZip2));
//            }

            clientDetail.setRaStreet1(cmsMaster.getCmsResAddress());
            clientDetail.setRaCity(cmsMaster.getCmsResCity());
            clientDetail.setRaState(cmsMaster.getCmsResState());

            resZip = (cmsMaster.getCmsResZip() != null && cmsMaster.getCmsResZip() != 0) ? String.valueOf(cmsMaster.getCmsResZip()) : null;
            resZip2 = (cmsMaster.getCmsResZip2() != null && cmsMaster.getCmsResZip2() != 0) ? String.valueOf(cmsMaster.getCmsResZip2()) : null;

//            if(StringUtils.isNotEmpty(resZip) && StringUtils.isNotEmpty(resZip2)) {
            clientDetail.setRaZip(StringUtils.joinWithDelimiter("-", resZip, resZip2));
//            }
        }

        clientDetail.setRaCountry(country);
        clientDetail.setMailingCountry(country);

        clientDetail.setClientTz("US/Central");
        clientDetail.setRaceList(createRaceList());
        clientDetail.setEthnicity(getEthnicity());

        return clientDetail;
    }

    private String createValidZip(Integer zip, int minLength) {
        if (zip == null || zip == 0) {
            return null;
        }

        String zipStr = String.valueOf(zip);
        int zipLength = zipStr.length();

        if (zipLength < minLength) {
            for (int i = 0; i < minLength-zipLength; i++) {
                zipStr = "0" + zipStr;
            }
        }
        return zipStr;
    }

    public ArClient createArClient(Provider provider) {
        ArClient arClient = new ArClient();

        if (ddsRoot != null) {
            arClient.setCountyCode(StringUtils.isEmpty(ddsRoot.getClientCounty()) ? null : getArCounty(ddsRoot.getClientCounty()));
            arClient.setDiplomaDate(ddsRoot.getClientDateOfDiploma());
            arClient.setPrimaryDisability(getArDisability("p"));
            arClient.setSecondaryDisability(getArDisability("s"));
            arClient.setAdaptiveBehaviorIndicator(StringUtils.isNotEmpty(ddsRoot.getClientAdapt()) ? getArClientAdapt() : null);
            arClient.setPrimaryHomeLanguage(StringUtils.isNotEmpty(ddsRoot.getClientLanguage()) ? getArLanguage() : null);
            arClient.setNumberInHousehold(ddsRoot.getClientHouse());
            arClient.setDdsId(ddsRoot.getClientId());
            arClient.setDdsStatus(StringUtils.isNotEmpty(ddsRoot.getClientStatus()) ? getArDdsStatus() : null);
        }

        if (cmsMaster != null) {
            arClient.setCmsId(cmsMaster.getCmsId());
            arClient.setCmsStatusCode(StringUtils.isEmpty(cmsMaster.getCmsStatus()) ? null : getArCmsStatus());
            arClient.setCmsApplyDate(cmsMaster.getCmsDateOfAppl());
            arClient.setCmsReapplyDate(cmsMaster.getCmsReappDate());
            arClient.setCmsCommOfficeAreaCode(cmsMaster.getCmsCommBasedOfcArea() != null ? getArCmsOfficeAreaCode() : null);
            arClient.setCmsMedicaidStatus(StringUtils.isNotEmpty(cmsMaster.getCmsMedicaidStatus()) ? getArCmsMedicaidStatus() : null);
            arClient.setCmsMedicaidCategory(StringUtils.isNotEmpty(cmsMaster.getCmsMedicaidCategory()) ?
                    getArCmsMedicaidCategory(cmsMaster.getCmsMedicaidCategory()) : null);
            arClient.setCmsMedicaidProviderNo(cmsMaster.getCmsMedicaidProvNo());
            arClient.setCmsPrimaryPhysician(cmsMaster.getCmsPrimaryPhysician());
            arClient.setThirdPartyLiability(StringUtils.isNotEmpty(cmsMaster.getCms3ptyl()) ? getArThirdPartyLiability() : null);
            arClient.setNumberInHousehold((cmsMaster.getCmsNumberInHousehold() != null && cmsMaster.getCmsNumberInHousehold() != 0) ?
                    arClient.getNumberInHousehold() : String.valueOf(cmsMaster.getCmsNumberInHousehold()));
        }

        if (ddsField != null) {
            arClient.setDdsCaseloadStatus(StringUtils.isEmpty(ddsField.getFieldCaseloadStatus()) ? null
                    : ddsField.getFieldCaseloadStatus().equals("0") ? ArCaseloadStatus.U : getArCaseloadStatus());
            arClient.setDdsCaseloadCloseReason(StringUtils.isEmpty(ddsField.getFieldReasonClosed()) ? null : getArReasonClosed());
        }

        if (ddsCmFinance != null) {
            arClient.setMonthlyMedicalBill(ddsCmFinance.getDdcmMonthlybills() == null ? null : Double.valueOf(ddsCmFinance.getDdcmMonthlybills()));
            arClient.setSsnDenialLetterDate(ddsCmFinance.getDdcmDenyResSsi());
            arClient.setMedicaidDenialLetterDate(ddsCmFinance.getDdcmDenyResMedicaid());
            arClient.setReceivingSobra(ddsCmFinance.getDdcmApplysobra() == null ? null : convertToBoolean(ddsCmFinance.getDdcmApplysobra()));
            arClient.setReceivingMedicaid(ddsCmFinance.getDdcmApplymedicaid() == null ? null : convertToBoolean(ddsCmFinance.getDdcmApplymedicaid()));
            arClient.setReceivingSsi(ddsCmFinance.getDdcmApplyssi() == null ? null : convertToBoolean(ddsCmFinance.getDdcmApplyssi()));
            arClient.setMedicallyNeedy(ddsCmFinance.getDdcmApplyneedy() == null ? null : convertToBoolean(ddsCmFinance.getDdcmApplyneedy()));
            arClient.setResources5WeekSpinDown(ddsCmFinance.getDdcmDenyRes5week());
            arClient.setDisabilitySsi(ddsCmFinance.getDdcmDenyDisSsi());
            arClient.setDisabilityMedicaidDenialDate(ddsCmFinance.getDdcmDenyDisMedicaid());
            arClient.setSobraAppliedDate(ddsCmFinance.getDdcmSobraDate());
            arClient.setNeedyDeterminationDate(ddsCmFinance.getDdcmNeedyDate());
            arClient.setMedicaidDeterminationDate(ddsCmFinance.getDdcmMedicaidDate());
            arClient.setSsiDeterminationDate(ddsCmFinance.getDdcmSsiDate());
            arClient.setParentsRefusalDate(ddsCmFinance.getDdcmParentRefusalDate());
        }

//        if (mergedDdsChild != null) {
//            arClient.setMonthlyMedicalBill(mergedDdsChild.getDdcmMonthlybills() == null ? null : Double.valueOf(mergedDdsChild.getDdcmMonthlybills()));
//            arClient.setSsnDenialLetterDate(mergedDdsChild.getDdcmDenyDisSsi());
//            arClient.setMedicaidDenialLetterDate(mergedDdsChild.getDdcmDenyResMedicaid());
//            arClient.setReceivingSobra(mergedDdsChild.getDdcmApplysobra() == null ? null : convertToBoolean(mergedDdsChild.getDdcmApplysobra()));
//            arClient.setReceivingMedicaid(mergedDdsChild.getDdcmApplymedicaid() == null ? null : convertToBoolean(mergedDdsChild.getDdcmApplymedicaid()));
//            arClient.setReceivingSsi(mergedDdsChild.getDdcmApplyssi() == null ? null : convertToBoolean(mergedDdsChild.getDdcmApplyssi()));
//            arClient.setMedicallyNeedy(mergedDdsChild.getDdcmApplyneedy() == null ? null : convertToBoolean(mergedDdsChild.getDdcmApplyneedy()));
//            arClient.setResources5WeekSpinDown(mergedDdsChild.getDdcmDenyRes5week());
//            arClient.setDisabilitySsi(mergedDdsChild.getDdcmDenyDisSsi());
//            arClient.setDisabilityMedicaidDenialDate(mergedDdsChild.getDdcmDenyDisMedicaid());
//            arClient.setSobraAppliedDate(mergedDdsChild.getDdcmSobraDate());
//            arClient.setNeedyDeterminationDate(mergedDdsChild.getDdcmNeedyDate());
//            arClient.setMedicaidDeterminationDate(mergedDdsChild.getDdcmMedicaidDate());
//            arClient.setSsiDeterminationDate(mergedDdsChild.getDdcmSsiDate());
//            arClient.setParentsRefusalDate(mergedDdsChild.getDdcmParentRefusalDate());
//            arClient.setDdsCaseloadStatus(StringUtils.isEmpty(mergedDdsChild.getFieldCaseloadStatus()) ? null
//                    : mergedDdsChild.getFieldCaseloadStatus().equals("0") ? ArCaseloadStatus.U : getArCaseloadStatus());
//            arClient.setDdsCaseloadCloseReason(StringUtils.isEmpty(mergedDdsChild.getFieldReasonClosed()) ? null : getArReasonClosed());
//        }

        FormIdGenerator formIdGenerator = new FormIdGeneratorBean();
        arClient.setFormId(formIdGenerator.generate(provider, CommonForm.FT_AR_IDF_EXT, false));

        arClient.setMedicaidDenials(createMedicaidDenialList());
        arClient.setFamilyMembers(createArClientFamilyMembers());
        arClient.setEligibilityDurations(createArEligibilityDurations());
        arClient.setArClientGuardianAddresses(createGuardianAddress());
        arClient.setProvider(provider);
        setArProgram(arClient);

        return arClient;
    }

    private ArCmsOfficeAreaCode getArCmsOfficeAreaCode() {
        for (ArCmsOfficeAreaCode areaCode : ArCmsOfficeAreaCode.values()) {
            if (areaCode.getCode().equals(String.valueOf(cmsMaster.getCmsCommBasedOfcArea()))) {
                return areaCode;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Office Area Code = " + cmsMaster.getCmsCommBasedOfcArea() + " " + getClientInfo());
        return null;
    }

    private YesNoEnum getArThirdPartyLiability() {
        for (YesNoEnum yesNoEnum : YesNoEnum.values() ) {
            if (yesNoEnum.toString().equalsIgnoreCase(cmsMaster.getCms3ptyl())) {
                return yesNoEnum;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Third Party Liability = " + cmsMaster.getCms3ptyl() + " " + getClientInfo());
        return null;
    }

    private YesNoEnum getArCmsMedicaidStatus() {
        for (YesNoEnum yesNoEnum : YesNoEnum.values() ) {
            if (yesNoEnum.toString().equalsIgnoreCase(cmsMaster.getCmsMedicaidStatus())) {
                return yesNoEnum;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Medicaid Status = " + cmsMaster.getCmsMedicaidStatus() + " " + getClientInfo());
        return null;
    }

    private ArAdaptiveBehaviorIndicator getArClientAdapt() {
        for (ArAdaptiveBehaviorIndicator ai : ArAdaptiveBehaviorIndicator.values()) {
            if (ai.getCode().equalsIgnoreCase(ddsRoot.getClientAdapt())) {
                return ai;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] Adaptive Behavior Indicator = " + ddsRoot.getClientAdapt() + " " + getClientInfo());
        return null;
    }

    private CmsMedicaidCategory getArCmsMedicaidCategory(String code) {
        for (CmsMedicaidCategory mc : CmsMedicaidCategory.values()) {
            if (mc.getCode().equals(code)) {
                return mc;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Medicaid Category = " + code + " " + getClientInfo());
        return null;
    }


    private  ArCounty getArCounty(String code) {
        for (ArCounty ac : ArCounty.values()) {
            if (ac.toString().equals(code)) return ac;
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] County = " + code + " " + getClientInfo());
        return null;
    }

    private  ArLanguage getArLanguage() {
        for (ArLanguage arLanguage : ArLanguage.values()) {
            if (arLanguage.getCode().equalsIgnoreCase(ddsRoot.getClientLanguage())) {
                return arLanguage;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] Language = " + ddsRoot.getClientLanguage() + " " + getClientInfo());
        return null;
    }

    private  ArCmsStatus getArCmsStatus() {
        for (ArCmsStatus arCmsStatus : ArCmsStatus.values()) {
            if (arCmsStatus.toString().equalsIgnoreCase(cmsMaster.getCmsStatus())) {
                return arCmsStatus;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] CMS Status for AR Client = " + cmsMaster.getCmsStatus() + " " + getClientInfo());
        return null;
    }

    private ArReasonClosed getArReasonClosed() {
        for (ArReasonClosed arReasonClosed : ArReasonClosed.values()) {
            if (arReasonClosed.toString().equalsIgnoreCase(ddsField.getFieldReasonClosed())) {
                return arReasonClosed;
            }
        }
        logger.info("[ERROR:MAPPING_NOT_FOUND] Reason Closed = " + ddsField.getFieldReasonClosed() + " " + getClientInfo());
        return null;
    }

    private ArCaseloadStatus getArCaseloadStatus() {
        for (ArCaseloadStatus arCaseloadStatus : ArCaseloadStatus.values()) {
            if (arCaseloadStatus.name().equalsIgnoreCase(ddsField.getFieldCaseloadStatus())) {
                return arCaseloadStatus;
            }
        }
        logger.info("[ERROR:MAPPING_NOT_FOUND] Caseload Status = " + ddsField.getFieldCaseloadStatus() + " " + getClientInfo());
        return null;
    }

    private ArGuardianType getArGuardianType(String guardianType) {
        for (ArGuardianType arGuardianType : ArGuardianType.values()) {
            if (arGuardianType.getCode().equalsIgnoreCase(guardianType)) {
                return arGuardianType;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] Guardian Type = " + guardianType + " " + getClientInfo());
        return null;
    }

    private ArGuardianType getArRelationType(Integer relationType) {
        for (ArGuardianType arRelationType : ArGuardianType.values()) {
            if (arRelationType.getCode().equalsIgnoreCase(String.valueOf(relationType))) {
                return arRelationType;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] Relation Type = " + relationType + " " + getClientInfo());
        return null;
    }

    private ArDdsStatus getArDdsStatus() {
        for (ArDdsStatus arDdsStatus : ArDdsStatus.values()) {
            if (arDdsStatus.getCode().equalsIgnoreCase(ddsRoot.getClientStatus())) {
                return arDdsStatus;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] DDS Status = " + ddsRoot.getClientStatus() + " " + getClientInfo());
        return null;
    }

    private ArDisability getArDisability(String type) {

        String disability = type.equals("p") ? ddsRoot.getClientPrimary() : ddsRoot.getClientSecondary();

        if (StringUtils.isEmpty(disability)) {
            return null;
        }

        String disabilityCode = disability.startsWith("0") ? disability : "0" + disability;
        for (ArDisability arDisability : ArDisability.values()) {
            if (arDisability.getCode().equalsIgnoreCase(disabilityCode)) {
                return arDisability;
            }
        }

        logger.info("[ERROR:MAPPING_NOT_FOUND] Disability = " + disability + " Type : " + (type.equals("p") ? "Primary" : "Secondary") + " " + getClientInfo());
        return null;
    }

    private void setArProgram(ArClient arClient) {
        if (cmsMaster != null && cmsMaster.getValidData() == 1) {
            arClient.setTitleFiveProgram(true);
        }

        if (ddsRoot != null) {
            int allStatus = ddsRoot.getValidData();
            while (true) {
                int status = allStatus % 10;
                allStatus = allStatus / 10;
                if (status > 0) {
                    if (status == 1) {
                        arClient.setEiProgram(true);
                    } else if (status == 2) {
                        arClient.setDdtcsProgram(true);
                    } else if (status == 3) {
                        arClient.setEcProgram(true);
                    }
                } else {
                    break;
                }
            }
        }
    }

    private Integer getEthnicity() {
        String ethnicity;
        Integer ethnicityCode = null;
        if(cmsMaster != null && StringUtils.isNotEmpty(cmsMaster.getCmsRace())) {
            ethnicity = cmsMaster.getCmsRace() + (StringUtils.isNotEmpty(cmsMaster.getCmsEthnicCode()) ? cmsMaster.getCmsEthnicCode() : "");
            ethnicityCode = cmsMasterClientEthnicityMap.get(ethnicity);
        }

        if (ethnicityCode == null && cmsMaster != null && StringUtils.isNotEmpty(cmsMaster.getCmsEthnicCode())) {
            logger.info("[ERROR:MAPPING_NOT_FOUND] Ethnicity = " + cmsMaster.getCmsEthnicCode() + ", " + getClientInfo());
        }

        return ethnicityCode;
    }

    public List<ArClientFamilyMember> createArClientFamilyMembers() {
        if (ddsCmFinance == null) {
            return null;
        }

        List<ArClientFamilyMember> arClientFamilyMembers = new ArrayList<ArClientFamilyMember>();

        ArClientFamilyMember arClientFamilyMember = new ArClientFamilyMember();
        arClientFamilyMember.setName(ddsCmFinance.getDdcmCmfName());
        arClientFamilyMember.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge()));
        arClientFamilyMember.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome());
        arClientFamilyMember.setRelationship(ddsCmFinance.getDdcmCmfRelation() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation()) : null);
        arClientFamilyMember.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember)) {
            arClientFamilyMembers.add(arClientFamilyMember);
        }

        ArClientFamilyMember arClientFamilyMember1 = new ArClientFamilyMember();
        arClientFamilyMember1.setName(ddsCmFinance.getDdcmCmfName1());
        arClientFamilyMember1.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge1()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge1()));
        arClientFamilyMember1.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome1());
        arClientFamilyMember1.setRelationship(ddsCmFinance.getDdcmCmfRelation1() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation1()) : null);
        arClientFamilyMember1.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource1());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember1)) {
            arClientFamilyMembers.add(arClientFamilyMember1);
        }

        ArClientFamilyMember arClientFamilyMember2 = new ArClientFamilyMember();
        arClientFamilyMember2.setName(ddsCmFinance.getDdcmCmfName2());
        arClientFamilyMember2.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge2()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge2()));
        arClientFamilyMember2.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome2());
        arClientFamilyMember2.setRelationship(ddsCmFinance.getDdcmCmfRelation2() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation2()) : null);
        arClientFamilyMember2.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource2());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember2)) {
            arClientFamilyMembers.add(arClientFamilyMember2);
        }

        ArClientFamilyMember arClientFamilyMember3 = new ArClientFamilyMember();
        arClientFamilyMember3.setName(ddsCmFinance.getDdcmCmfName3());
        arClientFamilyMember3.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge3()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge3()));
        arClientFamilyMember3.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome3());
        arClientFamilyMember3.setRelationship(ddsCmFinance.getDdcmCmfRelation3() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation3()) : null);
        arClientFamilyMember3.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource3());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember3)) {
            arClientFamilyMembers.add(arClientFamilyMember3);
        }

        ArClientFamilyMember arClientFamilyMember4 = new ArClientFamilyMember();
        arClientFamilyMember4.setName(ddsCmFinance.getDdcmCmfName4());
        arClientFamilyMember4.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge4()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge4()));
        arClientFamilyMember4.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome4());
        arClientFamilyMember4.setRelationship(ddsCmFinance.getDdcmCmfRelation4() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation4()) : null);
        arClientFamilyMember4.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource4());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember4)) {
            arClientFamilyMembers.add(arClientFamilyMember4);
        }

        ArClientFamilyMember arClientFamilyMember5 = new ArClientFamilyMember();
        arClientFamilyMember5.setName(ddsCmFinance.getDdcmCmfName5());
        arClientFamilyMember5.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge5()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge5()));
        arClientFamilyMember5.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome5());
        arClientFamilyMember5.setRelationship(ddsCmFinance.getDdcmCmfRelation5() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation5()) : null);
        arClientFamilyMember5.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource5());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember5)) {
            arClientFamilyMembers.add(arClientFamilyMember5);
        }

        ArClientFamilyMember arClientFamilyMember6 = new ArClientFamilyMember();
        arClientFamilyMember6.setName(ddsCmFinance.getDdcmCmfName6());
        arClientFamilyMember6.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge6()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge6()));
        arClientFamilyMember6.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome6());
        arClientFamilyMember6.setRelationship(ddsCmFinance.getDdcmCmfRelation6() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation6()) : null);
        arClientFamilyMember6.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource6());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember6)) {
            arClientFamilyMembers.add(arClientFamilyMember6);
        }

        ArClientFamilyMember arClientFamilyMember7 = new ArClientFamilyMember();
        arClientFamilyMember7.setName(ddsCmFinance.getDdcmCmfName7());
        arClientFamilyMember7.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge7()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge7()));
        arClientFamilyMember7.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome7());
        arClientFamilyMember7.setRelationship(ddsCmFinance.getDdcmCmfRelation7() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation7()) : null);
        arClientFamilyMember7.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource7());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember7)) {
            arClientFamilyMembers.add(arClientFamilyMember7);
        }

        ArClientFamilyMember arClientFamilyMember8 = new ArClientFamilyMember();
        arClientFamilyMember8.setName(ddsCmFinance.getDdcmCmfName8());
        arClientFamilyMember8.setAge(StringUtils.isEmpty(ddsCmFinance.getDdcmCmfAge8()) ? null : Integer.valueOf(ddsCmFinance.getDdcmCmfAge8()));
        arClientFamilyMember8.setMonthlyIncome(ddsCmFinance.getDdcmCmfIncome8());
        arClientFamilyMember8.setRelationship(ddsCmFinance.getDdcmCmfRelation8() != null ? getArRelationType(ddsCmFinance.getDdcmCmfRelation8()) : null);
        arClientFamilyMember8.setSourceOfIncome(ddsCmFinance.getDdcmCmfSource8());

        if (isNotEmptyArClientFamilyMember(arClientFamilyMember8)) {
            arClientFamilyMembers.add(arClientFamilyMember8);
        }
        return arClientFamilyMembers;
    }

    public List<ArEligibilityDuration> createArEligibilityDurations() {
        if (cmsMaster == null) {
            return null;
        }

        List<ArEligibilityDuration> arEligibilityDurations = new ArrayList<ArEligibilityDuration>();

        ArEligibilityDuration arEligibilityDuration1 = new ArEligibilityDuration();
        arEligibilityDuration1.setBeginDate(cmsMaster.getCmsEligBegDate1());
        arEligibilityDuration1.setEndDate(cmsMaster.getCmsEligEndDate1());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration1)) {
            arEligibilityDurations.add(arEligibilityDuration1);
        }

        ArEligibilityDuration arEligibilityDuration2 = new ArEligibilityDuration();
        arEligibilityDuration2.setBeginDate(cmsMaster.getCmsEligBegDate2());
        arEligibilityDuration2.setEndDate(cmsMaster.getCmsEligEndDate2());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration2)) {
            arEligibilityDurations.add(arEligibilityDuration2);
        }

        ArEligibilityDuration arEligibilityDuration3 = new ArEligibilityDuration();
        arEligibilityDuration3.setBeginDate(cmsMaster.getCmsEligBegDate3());
        arEligibilityDuration3.setEndDate(cmsMaster.getCmsEligEndDate3());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration3)) {
            arEligibilityDurations.add(arEligibilityDuration3);
        }

        ArEligibilityDuration arEligibilityDuration4 = new ArEligibilityDuration();
        arEligibilityDuration4.setBeginDate(cmsMaster.getCmsEligBegDate4());
        arEligibilityDuration4.setEndDate(cmsMaster.getCmsEligEndDate4());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration4)) {
            arEligibilityDurations.add(arEligibilityDuration4);
        }

        ArEligibilityDuration arEligibilityDuration5 = new ArEligibilityDuration();
        arEligibilityDuration5.setBeginDate(cmsMaster.getCmsEligBegDate5());
        arEligibilityDuration5.setEndDate(cmsMaster.getCmsEligEndDate5());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration5)) {
            arEligibilityDurations.add(arEligibilityDuration5);
        }

        ArEligibilityDuration arEligibilityDuration6 = new ArEligibilityDuration();
        arEligibilityDuration6.setBeginDate(cmsMaster.getCmsEligBegDate6());
        arEligibilityDuration6.setEndDate(cmsMaster.getCmsEligEndDate6());

        if (isNotEmptyArEligibilityDuration(arEligibilityDuration6)) {
            arEligibilityDurations.add(arEligibilityDuration6);
        }

        return arEligibilityDurations;
    }

    public List<ArClientMedicaidDenial> createMedicaidDenialList() {

        if (CollectionUtils.isEmpty(medicaidDenialList)) {
            return null;
        }

        List<ArClientMedicaidDenial> arClientMedicaidDenials = new ArrayList<ArClientMedicaidDenial>();
        for (MedicaidDenial medicaidDenial : medicaidDenialList) {
            ArClientMedicaidDenial arClientMedicaidDenial = new ArClientMedicaidDenial();
            arClientMedicaidDenial.setMedicaidDenialBeginDate(medicaidDenial.getMedicaidDenialStartDate());
            arClientMedicaidDenial.setMedicaidDenialEndDate(medicaidDenial.getMedicaidDenialEndDate());
            arClientMedicaidDenial.setMedicaidDenialOptionToDelete(
                    StringUtils.isEmpty(medicaidDenial.getMedicaidDenialOptionDelete()) ? null : convertToBoolean(medicaidDenial.getMedicaidDenialOptionDelete()));
            arClientMedicaidDenials.add(arClientMedicaidDenial);
        }

        return arClientMedicaidDenials;
    }

    public List<Race> createRaceList() {
        List<Race> raceList = new ArrayList<Race>();
        Integer raceCode = null;
        String race = null;

        if (cmsMaster != null && StringUtils.isNotEmpty(cmsMaster.getCmsRace())) {
            race = cmsMaster.getCmsRace();
            raceCode = cmsMasterClientRaceMap.get(race);
        } else if (ddsRoot != null && StringUtils.isNotEmpty(ddsRoot.getClientRace())) {
            race = ddsRoot.getClientRace();
            raceCode = ddsRootClientRaceMap.get(race);
        }

        if (raceCode != null) {
            Race therapRace = new Race();
            therapRace.setCode(raceCode);
            raceList.add(therapRace);
        } else if (StringUtils.isNotEmpty(race)) {
            logger.info("[ERROR:MAPPING_NOT_FOUND] Race= " + race + " " + getClientInfo());
        }

        return raceList;
    }

    public  List<ArClientGuardianAddress> createGuardianAddress() {
        List<ArClientGuardianAddress> guardianAddressList = new ArrayList<ArClientGuardianAddress>();

        if (ddsField != null) {
            ArClientGuardianAddress fatherAddress = new ArClientGuardianAddress();
            setGuardianName(fatherAddress, ddsField.getFieldFatherName());
            fatherAddress.setAddress(ddsField.getFieldFatherAddress());
            fatherAddress.setCity(ddsField.getFieldFatherCity());
            fatherAddress.setCounty(StringUtils.isNotEmpty(ddsField.getFieldFatherCounty()) ? getArCounty(ddsField.getFieldFatherCounty()) : null);
            fatherAddress.setState(ddsField.getFieldFatherState());
            fatherAddress.setZip(
                    StringUtils.isNotEmpty(ddsField.getFieldFatherZip()) && ddsField.getFieldFatherZip().matches("0*") ? null : ddsField.getFieldFatherZip());
            fatherAddress.setPhone1(
                    StringUtils.isNotEmpty(ddsField.getFieldFatherPhoneRes()) && ddsField.getFieldFatherPhoneRes().matches("0*") ? null : ddsField.getFieldFatherPhoneRes());
            fatherAddress.setPhone2(
                    StringUtils.isNotEmpty(ddsField.getFieldFatherPhoneBus()) && ddsField.getFieldFatherPhoneBus().matches("0*") ? null : ddsField.getFieldFatherPhoneBus());
            fatherAddress.setPhone3(
                    StringUtils.isNotEmpty(ddsField.getFieldFatherPhoneMes()) && ddsField.getFieldFatherPhoneMes().matches("0*") ? null : ddsField.getFieldFatherPhoneMes());

            if (isNotEmptyArClientGuardianAddress(fatherAddress)) {
                fatherAddress.setGuardianType(ArGuardianType.FATHER);
                guardianAddressList.add(fatherAddress);
            }

            ArClientGuardianAddress motherAddress = new ArClientGuardianAddress();
            setGuardianName(motherAddress, ddsField.getFieldMotherName());
            motherAddress.setAddress(ddsField.getFieldMotherAddress());
            motherAddress.setCity(ddsField.getFieldMotherCity());
            motherAddress.setCounty(StringUtils.isNotEmpty(ddsField.getFieldMotherCounty()) ? getArCounty(ddsField.getFieldMotherCounty()) : null);
            motherAddress.setState(ddsField.getFieldMotherState());
            motherAddress.setZip(
                    StringUtils.isNotEmpty(ddsField.getFieldMotherZip()) && ddsField.getFieldMotherZip().matches("0*") ? null : ddsField.getFieldMotherZip());
            motherAddress.setPhone1(
                    StringUtils.isNotEmpty(ddsField.getFieldMotherPhoneRes()) && ddsField.getFieldMotherPhoneRes().matches("0*") ? null : ddsField.getFieldMotherPhoneRes());
            motherAddress.setPhone2(
                    StringUtils.isNotEmpty(ddsField.getFieldMotherPhoneBus()) && ddsField.getFieldMotherPhoneBus().matches("0*") ? null : ddsField.getFieldMotherPhoneBus());
            motherAddress.setPhone3(
                    StringUtils.isNotEmpty(ddsField.getFieldMotherPhoneMes()) && ddsField.getFieldMotherPhoneMes().matches("0*") ? null : ddsField.getFieldMotherPhoneMes());

            if (isNotEmptyArClientGuardianAddress(motherAddress)) {
                motherAddress.setGuardianType(ArGuardianType.MOTHER);
                guardianAddressList.add(motherAddress);
            }
        }

        if (ddsFinancial != null) {
            ArClientGuardianAddress guardianAddress = new ArClientGuardianAddress();
            guardianAddress.setGuardianType(StringUtils.isEmpty(ddsFinancial.getGuardianType()) ? null : getArGuardianType(ddsFinancial.getGuardianType()));
            setGuardianName(guardianAddress, ddsFinancial.getGuardianName());
            guardianAddress.setAddress(ddsFinancial.getGuardianAddress());
            guardianAddress.setCity(ddsFinancial.getGuardianCity());
            guardianAddress.setState(ddsFinancial.getGuardianState());
            guardianAddress.setZip(
                    StringUtils.isNotEmpty(ddsFinancial.getGuardianZip()) && ddsFinancial.getGuardianZip().matches("0*") ? null : ddsFinancial.getGuardianZip());
            guardianAddress.setPhone1(
                    StringUtils.isNotEmpty(ddsFinancial.getGuardianPhone()) && ddsFinancial.getGuardianPhone().matches("0*") ? null : ddsFinancial.getGuardianPhone());

            if (isNotEmptyArClientGuardianAddress(guardianAddress)) {
                guardianAddressList.add(guardianAddress);
            }
        }


        return guardianAddressList;
    }

    public void setGuardianName(ArClientGuardianAddress address, String guardianName) {
        if (StringUtils.isEmpty(guardianName)) {
            return;
        }

        String[] names = guardianName.split("\\s");
        address.setLastName(names[0]);

        if (names.length == 3) {
            address.setFirstName(names[1]);
            address.setMiddleName(names[2]);
        } else if (names.length > 1) {
            address.setFirstName(StringUtils.joinWithDelimiter(" ", Arrays.copyOfRange(names, 1, names.length)));
            address.setMiddleName(null);
        }
    }

    public Oversight createArClientOversight(Client client, Provider provider, String assignedId) {

        Oversight oversight = new Oversight();

        oversight.setClient(client);
        oversight.setAssignedId(assignedId);
        oversight.setAgency(provider);

        client.setOversights(new HashSet<Oversight>());
        client.getOversights().add(oversight);

        return oversight;
    }

    public Boolean convertToBoolean(String value) {
        return value.equalsIgnoreCase("Y");
    }

//    public String removeExtraChars(String name, int maxLength) {
//        if (name.length() > maxLength) {
//            name = name.substring(0, maxLength);
//        }
//        return name;
//    }

    private boolean isNotEmptyArClientFamilyMember(ArClientFamilyMember arClientFamilyMember) {
        boolean age = arClientFamilyMember.getAge() != null;
        boolean monthlyIncome = arClientFamilyMember.getMonthlyIncome() != null;
        boolean sourceOfIncome = StringUtils.isNotEmpty(arClientFamilyMember.getSourceOfIncome());
        boolean name = StringUtils.isNotEmpty(arClientFamilyMember.getName());
        boolean relationship = arClientFamilyMember.getRelationship() != null;

        return (age || monthlyIncome || sourceOfIncome || name || relationship);
    }

    private boolean isNotEmptyArEligibilityDuration(ArEligibilityDuration arEligibilityDuration) {
        boolean beginDate = arEligibilityDuration.getBeginDate() != null;
        boolean endDate = arEligibilityDuration.getEndDate() != null;

        return (beginDate || endDate);
    }

    private boolean isNotEmptyArClientGuardianAddress(ArClientGuardianAddress arClientGuardianAddress) {
        boolean guardianType = arClientGuardianAddress.getGuardianType() != null;
        boolean address = StringUtils.isNotEmpty(arClientGuardianAddress.getAddress());
        boolean city = StringUtils.isNotEmpty(arClientGuardianAddress.getCity());
        boolean county = arClientGuardianAddress.getCounty() != null;
        boolean state = StringUtils.isNotEmpty(arClientGuardianAddress.getState());
        boolean zip = StringUtils.isNotEmpty(arClientGuardianAddress.getZip());
        boolean phone1 = arClientGuardianAddress.getPhone1() != null;
        boolean phone2 = arClientGuardianAddress.getPhone2() != null;
        boolean phone3 = arClientGuardianAddress.getPhone3() != null;

        return (guardianType || address || city || county || zip || state || phone1 || phone2 || phone3);
    }

    private String getClientInfo() {

        String name = null, ssnNumber = null;
        Long ddsId = null, cmsId = null;
        Date dateOfBirth = null;

        if (ddsRoot != null) {

            ssnNumber = ddsRoot.getClientSsn() != null ? String.valueOf(ddsRoot.getClientSsn()) : null;
            dateOfBirth = ddsRoot.getClientDateOfBirth();
            name = StringUtils.join(ddsRoot.getClientLastname(), ddsRoot.getClientFirstname(), ddsRoot.getClientMiddlename());
            ddsId = ddsRoot.getClientId();
        }

        if (cmsMaster != null) {

            ssnNumber = cmsMaster.getCmsSsn() != null ? String.valueOf(cmsMaster.getCmsSsn()) : null;
            dateOfBirth = cmsMaster.getCmsDob();
            name = cmsMaster.getCmsName();
            cmsId = cmsMaster.getCmsId();
        }

        return " Client Name : " + name +
                " DDS ID :" + ddsId +
                " CMS ID :" + cmsId +
                " SSN Number :" + ssnNumber +
                " Date of Birth :" + dateOfBirth;
    }

}
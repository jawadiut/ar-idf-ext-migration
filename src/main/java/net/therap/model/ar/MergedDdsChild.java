package net.therap.model.ar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author jawad
 * @since 6/10/14  9:51 AM
 */
@Entity
@Table(name = "TEMP_AR2_ALL_DDS_CHILD")
public class MergedDdsChild {
    @Id
    @Column(name = "DDS_Id")
    private Long ddsId;
    @Column(name = "DDCM_Monthly_Bills")
    private Long ddcmMonthlybills;
    @Column(name = "DDCM_Apply_Sobra")
    private String ddcmApplysobra;
    @Column(name = "DDCM_Apply_Needy")
    private String ddcmApplyneedy;
    @Column(name = "DDCM_Apply_Medicaid")
    private String ddcmApplymedicaid;
    @Column(name = "DDCM_Apply_SSI")
    private String ddcmApplyssi;
    @Column(name = "DDCM_CMF_NAME")
    private String ddcmCmfName;
    @Column(name = "DDCM_CMF_RELATION")
    private Integer ddcmCmfRelation;
    @Column(name = "DDCM_CMF_AGE")
    private String ddcmCmfAge;
    @Column(name = "DDCM_CMF_INCOME")
    private Double ddcmCmfIncome;
    @Column(name = "DDCM_CMF_SOURCE")
    private String ddcmCmfSource;
    @Column(name = "DDCM_CMF_NAME_1")
    private String ddcmCmfName1;
    @Column(name = "DDCM_CMF_RELATION_1")
    private Integer ddcmCmfRelation1;
    @Column(name = "DDCM_CMF_AGE_1")
    private String ddcmCmfAge1;
    @Column(name = "DDCM_CMF_INCOME_1")
    private Double ddcmCmfIncome1;
    @Column(name = "DDCM_CMF_SOURCE_1")
    private String ddcmCmfSource1;
    @Column(name = "DDCM_CMF_NAME_2")
    private String ddcmCmfName2;
    @Column(name = "DDCM_CMF_RELATION_2")
    private Integer ddcmCmfRelation2;
    @Column(name = "DDCM_CMF_AGE_2")
    private String ddcmCmfAge2;
    @Column(name = "DDCM_CMF_INCOME_2")
    private Double ddcmCmfIncome2;
    @Column(name = "DDCM_CMF_SOURCE_2")
    private String ddcmCmfSource2;
    @Column(name = "DDCM_CMF_NAME_3")
    private String ddcmCmfName3;
    @Column(name = "DDCM_CMF_RELATION_3")
    private Integer ddcmCmfRelation3;
    @Column(name = "DDCM_CMF_AGE_3")
    private String ddcmCmfAge3;
    @Column(name = "DDCM_CMF_INCOME_3")
    private Double ddcmCmfIncome3;
    @Column(name = "DDCM_CMF_SOURCE_3")
    private String ddcmCmfSource3;
    @Column(name = "DDCM_CMF_NAME_4")
    private String ddcmCmfName4;
    @Column(name = "DDCM_CMF_RELATION_4")
    private Integer ddcmCmfRelation4;
    @Column(name = "DDCM_CMF_AGE_4")
    private String ddcmCmfAge4;
    @Column(name = "DDCM_CMF_INCOME_4")
    private Double ddcmCmfIncome4;
    @Column(name = "DDCM_CMF_SOURCE_4")
    private String ddcmCmfSource4;
    @Column(name = "DDCM_CMF_NAME_5")
    private String ddcmCmfName5;
    @Column(name = "DDCM_CMF_RELATION_5")
    private Integer ddcmCmfRelation5;
    @Column(name = "DDCM_CMF_AGE_5")
    private String ddcmCmfAge5;
    @Column(name = "DDCM_CMF_INCOME_5")
    private Double ddcmCmfIncome5;
    @Column(name = "DDCM_CMF_SOURCE_5")
    private String ddcmCmfSource5;
    @Column(name = "DDCM_CMF_NAME_6")
    private String ddcmCmfName6;
    @Column(name = "DDCM_CMF_RELATION_6")
    private Integer ddcmCmfRelation6;
    @Column(name = "DDCM_CMF_AGE_6")
    private String ddcmCmfAge6;
    @Column(name = "DDCM_CMF_INCOME_6")
    private Double ddcmCmfIncome6;
    @Column(name = "DDCM_CMF_SOURCE_6")
    private String ddcmCmfSource6;
    @Column(name = "DDCM_CMF_NAME_7")
    private String ddcmCmfName7;
    @Column(name = "DDCM_CMF_RELATION_7")
    private Integer ddcmCmfRelation7;
    @Column(name = "DDCM_CMF_AGE_7")
    private String ddcmCmfAge7;
    @Column(name = "DDCM_CMF_INCOME_7")
    private Double ddcmCmfIncome7;
    @Column(name = "DDCM_CMF_SOURCE_7")
    private String ddcmCmfSource7;
    @Column(name = "DDCM_CMF_NAME_8")
    private String ddcmCmfName8;
    @Column(name = "DDCM_CMF_RELATION_8")
    private Integer ddcmCmfRelation8;
    @Column(name = "DDCM_CMF_AGE_8")
    private String ddcmCmfAge8;
    @Column(name = "DDCM_CMF_INCOME_8")
    private Double ddcmCmfIncome8;
    @Column(name = "DDCM_CMF_SOURCE_8")
    private String ddcmCmfSource8;
    @Column(name = "DDCM_DENY_RES_SSI")
    private Date ddcmDenyResSsi;
    @Column(name = "DDCM_DENY_RES_MEDICAID")
    private Date ddcmDenyResMedicaid;
    @Column(name = "DDCM_DENY_RES_5WEEK")
    private Date ddcmDenyRes5week;
    @Column(name = "DDCM_DENY_DIS_SSI")
    private Date ddcmDenyDisSsi;
    @Column(name = "DDCM_DENY_DIS_MEDICAID")
    private Date ddcmDenyDisMedicaid;
    @Column(name = "DDCM_SOBRA_DATE")
    private Date ddcmSobraDate;
    @Column(name = "DDCM_NEEDY_DATE")
    private Date ddcmNeedyDate;
    @Column(name = "DDCM_MEDICAID_DATE")
    private Date ddcmMedicaidDate;
    @Column(name = "DDCM_SSI_DATE")
    private Date ddcmSsiDate;
    @Column(name = "DDCM_PARENT_REFUSAL_DATE")
    private Date ddcmParentRefusalDate;
    @Column(name = "field_reason_closed")
    private String fieldReasonClosed;
    @Column(name = "field_caseload_status")
    private String fieldCaseloadStatus;
    @Column(name = "field_father_name")
    private String fieldFatherName;
    @Column(name = "field_father_address")
    private String fieldFatherAddress;
    @Column(name = "field_father_city")
    private String fieldFatherCity;
    @Column(name = "field_father_county")
    private String fieldFatherCounty;
    @Column(name = "field_father_state")
    private String fieldFatherState;
    @Column(name = "field_father_zip")
    private String fieldFatherZip;
    @Column(name = "field_father_phone_res_ac")
    private String fieldFatherPhoneResAc;
    @Column(name = "field_father_phone_res")
    private String fieldFatherPhoneRes;
    @Column(name = "field_father_phone_bus_ac")
    private String fieldFatherPhoneBusAc;
    @Column(name = "field_father_phone_bus")
    private String fieldFatherPhoneBus;
    @Column(name = "field_father_phone_mes_ac")
    private String fieldFatherPhoneMesAc;
    @Column(name = "field_father_phone_mes")
    private String fieldFatherPhoneMes;
    @Column(name = "field_mother_name")
    private String fieldMotherName;
    @Column(name = "field_mother_address")
    private String fieldMotherAddress;
    @Column(name = "field_mother_city")
    private String fieldMotherCity;
    @Column(name = "field_mother_county")
    private String fieldMotherCounty;
    @Column(name = "field_mother_state")
    private String fieldMotherState;
    @Column(name = "field_mother_zip")
    private String fieldMotherZip;
    @Column(name = "field_mother_phone_res_ac")
    private String fieldMotherPhoneResAc;
    @Column(name = "field_mother_phone_res")
    private String fieldMotherPhoneRes;
    @Column(name = "field_mother_phone_bus_ac")
    private String fieldMotherPhoneBusAc;
    @Column(name = "field_mother_phone_bus")
    private String fieldMotherPhoneBus;
    @Column(name = "field_mother_phone_mes_ac")
    private String fieldMotherPhoneMesAc;
    @Column(name = "field_mother_phone_mes")
    private String fieldMotherPhoneMes;
    @Column(name = "guardian_type")
    private String guardianType;
    @Column(name = "guardian_name")
    private String guardianName;
    @Column(name = "guardian_address")
    private String guardianAddress;
    @Column(name = "guardian_city")
    private String guardianCity;
    @Column(name = "guardian_state")
    private String guardianState;
    @Column(name = "guardian_zip")
    private String guardianZip;
    @Column(name = "guardian_phone")
    private String guardianPhone;

    public Long getDdsId() {
        return ddsId;
    }

    public void setDdsId(Long ddsId) {
        this.ddsId = ddsId;
    }

    public Long getDdcmMonthlybills() {
        return ddcmMonthlybills;
    }

    public void setDdcmMonthlybills(Long ddcmMonthlybills) {
        this.ddcmMonthlybills = ddcmMonthlybills;
    }

    public String getDdcmApplysobra() {
        return ddcmApplysobra;
    }

    public void setDdcmApplysobra(String ddcmApplysobra) {
        this.ddcmApplysobra = ddcmApplysobra;
    }

    public String getDdcmApplyneedy() {
        return ddcmApplyneedy;
    }

    public void setDdcmApplyneedy(String ddcmApplyneedy) {
        this.ddcmApplyneedy = ddcmApplyneedy;
    }

    public String getDdcmApplymedicaid() {
        return ddcmApplymedicaid;
    }

    public void setDdcmApplymedicaid(String ddcmApplymedicaid) {
        this.ddcmApplymedicaid = ddcmApplymedicaid;
    }

    public String getDdcmApplyssi() {
        return ddcmApplyssi;
    }

    public void setDdcmApplyssi(String ddcmApplyssi) {
        this.ddcmApplyssi = ddcmApplyssi;
    }

    public String getDdcmCmfName() {
        return ddcmCmfName;
    }

    public void setDdcmCmfName(String ddcmCmfName) {
        this.ddcmCmfName = ddcmCmfName;
    }

    public Integer getDdcmCmfRelation() {
        return ddcmCmfRelation;
    }

    public void setDdcmCmfRelation(Integer ddcmCmfRelation) {
        this.ddcmCmfRelation = ddcmCmfRelation;
    }

    public String getDdcmCmfAge() {
        return ddcmCmfAge;
    }

    public void setDdcmCmfAge(String ddcmCmfAge) {
        this.ddcmCmfAge = ddcmCmfAge;
    }

    public Double getDdcmCmfIncome() {
        return ddcmCmfIncome;
    }

    public void setDdcmCmfIncome(Double ddcmCmfIncome) {
        this.ddcmCmfIncome = ddcmCmfIncome;
    }

    public String getDdcmCmfSource() {
        return ddcmCmfSource;
    }

    public void setDdcmCmfSource(String ddcmCmfSource) {
        this.ddcmCmfSource = ddcmCmfSource;
    }

    public String getDdcmCmfName1() {
        return ddcmCmfName1;
    }

    public void setDdcmCmfName1(String ddcmCmfName1) {
        this.ddcmCmfName1 = ddcmCmfName1;
    }

    public Integer getDdcmCmfRelation1() {
        return ddcmCmfRelation1;
    }

    public void setDdcmCmfRelation1(Integer ddcmCmfRelation1) {
        this.ddcmCmfRelation1 = ddcmCmfRelation1;
    }

    public String getDdcmCmfAge1() {
        return ddcmCmfAge1;
    }

    public void setDdcmCmfAge1(String ddcmCmfAge1) {
        this.ddcmCmfAge1 = ddcmCmfAge1;
    }

    public Double getDdcmCmfIncome1() {
        return ddcmCmfIncome1;
    }

    public void setDdcmCmfIncome1(Double ddcmCmfIncome1) {
        this.ddcmCmfIncome1 = ddcmCmfIncome1;
    }

    public String getDdcmCmfSource1() {
        return ddcmCmfSource1;
    }

    public void setDdcmCmfSource1(String ddcmCmfSource1) {
        this.ddcmCmfSource1 = ddcmCmfSource1;
    }

    public String getDdcmCmfName2() {
        return ddcmCmfName2;
    }

    public void setDdcmCmfName2(String ddcmCmfName2) {
        this.ddcmCmfName2 = ddcmCmfName2;
    }

    public Integer getDdcmCmfRelation2() {
        return ddcmCmfRelation2;
    }

    public void setDdcmCmfRelation2(Integer ddcmCmfRelation2) {
        this.ddcmCmfRelation2 = ddcmCmfRelation2;
    }

    public String getDdcmCmfAge2() {
        return ddcmCmfAge2;
    }

    public void setDdcmCmfAge2(String ddcmCmfAge2) {
        this.ddcmCmfAge2 = ddcmCmfAge2;
    }

    public Double getDdcmCmfIncome2() {
        return ddcmCmfIncome2;
    }

    public void setDdcmCmfIncome2(Double ddcmCmfIncome2) {
        this.ddcmCmfIncome2 = ddcmCmfIncome2;
    }

    public String getDdcmCmfSource2() {
        return ddcmCmfSource2;
    }

    public void setDdcmCmfSource2(String ddcmCmfSource2) {
        this.ddcmCmfSource2 = ddcmCmfSource2;
    }

    public String getDdcmCmfName3() {
        return ddcmCmfName3;
    }

    public void setDdcmCmfName3(String ddcmCmfName3) {
        this.ddcmCmfName3 = ddcmCmfName3;
    }

    public Integer getDdcmCmfRelation3() {
        return ddcmCmfRelation3;
    }

    public void setDdcmCmfRelation3(Integer ddcmCmfRelation3) {
        this.ddcmCmfRelation3 = ddcmCmfRelation3;
    }

    public String getDdcmCmfAge3() {
        return ddcmCmfAge3;
    }

    public void setDdcmCmfAge3(String ddcmCmfAge3) {
        this.ddcmCmfAge3 = ddcmCmfAge3;
    }

    public Double getDdcmCmfIncome3() {
        return ddcmCmfIncome3;
    }

    public void setDdcmCmfIncome3(Double ddcmCmfIncome3) {
        this.ddcmCmfIncome3 = ddcmCmfIncome3;
    }

    public String getDdcmCmfSource3() {
        return ddcmCmfSource3;
    }

    public void setDdcmCmfSource3(String ddcmCmfSource3) {
        this.ddcmCmfSource3 = ddcmCmfSource3;
    }

    public String getDdcmCmfName4() {
        return ddcmCmfName4;
    }

    public void setDdcmCmfName4(String ddcmCmfName4) {
        this.ddcmCmfName4 = ddcmCmfName4;
    }

    public Integer getDdcmCmfRelation4() {
        return ddcmCmfRelation4;
    }

    public void setDdcmCmfRelation4(Integer ddcmCmfRelation4) {
        this.ddcmCmfRelation4 = ddcmCmfRelation4;
    }

    public String getDdcmCmfAge4() {
        return ddcmCmfAge4;
    }

    public void setDdcmCmfAge4(String ddcmCmfAge4) {
        this.ddcmCmfAge4 = ddcmCmfAge4;
    }

    public Double getDdcmCmfIncome4() {
        return ddcmCmfIncome4;
    }

    public void setDdcmCmfIncome4(Double ddcmCmfIncome4) {
        this.ddcmCmfIncome4 = ddcmCmfIncome4;
    }

    public String getDdcmCmfSource4() {
        return ddcmCmfSource4;
    }

    public void setDdcmCmfSource4(String ddcmCmfSource4) {
        this.ddcmCmfSource4 = ddcmCmfSource4;
    }

    public String getDdcmCmfName5() {
        return ddcmCmfName5;
    }

    public void setDdcmCmfName5(String ddcmCmfName5) {
        this.ddcmCmfName5 = ddcmCmfName5;
    }

    public Integer getDdcmCmfRelation5() {
        return ddcmCmfRelation5;
    }

    public void setDdcmCmfRelation5(Integer ddcmCmfRelation5) {
        this.ddcmCmfRelation5 = ddcmCmfRelation5;
    }

    public String getDdcmCmfAge5() {
        return ddcmCmfAge5;
    }

    public void setDdcmCmfAge5(String ddcmCmfAge5) {
        this.ddcmCmfAge5 = ddcmCmfAge5;
    }

    public Double getDdcmCmfIncome5() {
        return ddcmCmfIncome5;
    }

    public void setDdcmCmfIncome5(Double ddcmCmfIncome5) {
        this.ddcmCmfIncome5 = ddcmCmfIncome5;
    }

    public String getDdcmCmfSource5() {
        return ddcmCmfSource5;
    }

    public void setDdcmCmfSource5(String ddcmCmfSource5) {
        this.ddcmCmfSource5 = ddcmCmfSource5;
    }

    public String getDdcmCmfName6() {
        return ddcmCmfName6;
    }

    public void setDdcmCmfName6(String ddcmCmfName6) {
        this.ddcmCmfName6 = ddcmCmfName6;
    }

    public Integer getDdcmCmfRelation6() {
        return ddcmCmfRelation6;
    }

    public void setDdcmCmfRelation6(Integer ddcmCmfRelation6) {
        this.ddcmCmfRelation6 = ddcmCmfRelation6;
    }

    public String getDdcmCmfAge6() {
        return ddcmCmfAge6;
    }

    public void setDdcmCmfAge6(String ddcmCmfAge6) {
        this.ddcmCmfAge6 = ddcmCmfAge6;
    }

    public Double getDdcmCmfIncome6() {
        return ddcmCmfIncome6;
    }

    public void setDdcmCmfIncome6(Double ddcmCmfIncome6) {
        this.ddcmCmfIncome6 = ddcmCmfIncome6;
    }

    public String getDdcmCmfSource6() {
        return ddcmCmfSource6;
    }

    public void setDdcmCmfSource6(String ddcmCmfSource6) {
        this.ddcmCmfSource6 = ddcmCmfSource6;
    }

    public String getDdcmCmfName7() {
        return ddcmCmfName7;
    }

    public void setDdcmCmfName7(String ddcmCmfName7) {
        this.ddcmCmfName7 = ddcmCmfName7;
    }

    public Integer getDdcmCmfRelation7() {
        return ddcmCmfRelation7;
    }

    public void setDdcmCmfRelation7(Integer ddcmCmfRelation7) {
        this.ddcmCmfRelation7 = ddcmCmfRelation7;
    }

    public String getDdcmCmfAge7() {
        return ddcmCmfAge7;
    }

    public void setDdcmCmfAge7(String ddcmCmfAge7) {
        this.ddcmCmfAge7 = ddcmCmfAge7;
    }

    public Double getDdcmCmfIncome7() {
        return ddcmCmfIncome7;
    }

    public void setDdcmCmfIncome7(Double ddcmCmfIncome7) {
        this.ddcmCmfIncome7 = ddcmCmfIncome7;
    }

    public String getDdcmCmfSource7() {
        return ddcmCmfSource7;
    }

    public void setDdcmCmfSource7(String ddcmCmfSource7) {
        this.ddcmCmfSource7 = ddcmCmfSource7;
    }

    public String getDdcmCmfName8() {
        return ddcmCmfName8;
    }

    public void setDdcmCmfName8(String ddcmCmfName8) {
        this.ddcmCmfName8 = ddcmCmfName8;
    }

    public Integer getDdcmCmfRelation8() {
        return ddcmCmfRelation8;
    }

    public void setDdcmCmfRelation8(Integer ddcmCmfRelation8) {
        this.ddcmCmfRelation8 = ddcmCmfRelation8;
    }

    public String getDdcmCmfAge8() {
        return ddcmCmfAge8;
    }

    public void setDdcmCmfAge8(String ddcmCmfAge8) {
        this.ddcmCmfAge8 = ddcmCmfAge8;
    }

    public Double getDdcmCmfIncome8() {
        return ddcmCmfIncome8;
    }

    public void setDdcmCmfIncome8(Double ddcmCmfIncome8) {
        this.ddcmCmfIncome8 = ddcmCmfIncome8;
    }

    public String getDdcmCmfSource8() {
        return ddcmCmfSource8;
    }

    public void setDdcmCmfSource8(String ddcmCmfSource8) {
        this.ddcmCmfSource8 = ddcmCmfSource8;
    }

    public Date getDdcmDenyResSsi() {
        return ddcmDenyResSsi;
    }

    public void setDdcmDenyResSsi(Date ddcmDenyResSsi) {
        this.ddcmDenyResSsi = ddcmDenyResSsi;
    }

    public Date getDdcmDenyResMedicaid() {
        return ddcmDenyResMedicaid;
    }

    public void setDdcmDenyResMedicaid(Date ddcmDenyResMedicaid) {
        this.ddcmDenyResMedicaid = ddcmDenyResMedicaid;
    }

    public Date getDdcmDenyRes5week() {
        return ddcmDenyRes5week;
    }

    public void setDdcmDenyRes5week(Date ddcmDenyRes5week) {
        this.ddcmDenyRes5week = ddcmDenyRes5week;
    }

    public Date getDdcmDenyDisSsi() {
        return ddcmDenyDisSsi;
    }

    public void setDdcmDenyDisSsi(Date ddcmDenyDisSsi) {
        this.ddcmDenyDisSsi = ddcmDenyDisSsi;
    }

    public Date getDdcmDenyDisMedicaid() {
        return ddcmDenyDisMedicaid;
    }

    public void setDdcmDenyDisMedicaid(Date ddcmDenyDisMedicaid) {
        this.ddcmDenyDisMedicaid = ddcmDenyDisMedicaid;
    }

    public Date getDdcmSobraDate() {
        return ddcmSobraDate;
    }

    public void setDdcmSobraDate(Date ddcmSobraDate) {
        this.ddcmSobraDate = ddcmSobraDate;
    }

    public Date getDdcmNeedyDate() {
        return ddcmNeedyDate;
    }

    public void setDdcmNeedyDate(Date ddcmNeedyDate) {
        this.ddcmNeedyDate = ddcmNeedyDate;
    }

    public Date getDdcmMedicaidDate() {
        return ddcmMedicaidDate;
    }

    public void setDdcmMedicaidDate(Date ddcmMedicaidDate) {
        this.ddcmMedicaidDate = ddcmMedicaidDate;
    }

    public Date getDdcmSsiDate() {
        return ddcmSsiDate;
    }

    public void setDdcmSsiDate(Date ddcmSsiDate) {
        this.ddcmSsiDate = ddcmSsiDate;
    }

    public Date getDdcmParentRefusalDate() {
        return ddcmParentRefusalDate;
    }

    public void setDdcmParentRefusalDate(Date ddcmParentRefusalDate) {
        this.ddcmParentRefusalDate = ddcmParentRefusalDate;
    }

    public String getFieldReasonClosed() {
        return fieldReasonClosed;
    }

    public void setFieldReasonClosed(String fieldReasonClosed) {
        this.fieldReasonClosed = fieldReasonClosed;
    }

    public String getFieldCaseloadStatus() {
        return fieldCaseloadStatus;
    }

    public void setFieldCaseloadStatus(String fieldCaseloadStatus) {
        this.fieldCaseloadStatus = fieldCaseloadStatus;
    }

    public String getFieldFatherName() {
        return fieldFatherName;
    }

    public void setFieldFatherName(String fieldFatherName) {
        this.fieldFatherName = fieldFatherName;
    }

    public String getFieldFatherAddress() {
        return fieldFatherAddress;
    }

    public void setFieldFatherAddress(String fieldFatherAddress) {
        this.fieldFatherAddress = fieldFatherAddress;
    }

    public String getFieldFatherCity() {
        return fieldFatherCity;
    }

    public void setFieldFatherCity(String fieldFatherCity) {
        this.fieldFatherCity = fieldFatherCity;
    }

    public String getFieldFatherCounty() {
        return fieldFatherCounty;
    }

    public void setFieldFatherCounty(String fieldFatherCounty) {
        this.fieldFatherCounty = fieldFatherCounty;
    }

    public String getFieldFatherState() {
        return fieldFatherState;
    }

    public void setFieldFatherState(String fieldFatherState) {
        this.fieldFatherState = fieldFatherState;
    }

    public String getFieldFatherZip() {
        return fieldFatherZip;
    }

    public void setFieldFatherZip(String fieldFatherZip) {
        this.fieldFatherZip = fieldFatherZip;
    }

    public String getFieldFatherPhoneResAc() {
        return fieldFatherPhoneResAc;
    }

    public void setFieldFatherPhoneResAc(String fieldFatherPhoneResAc) {
        this.fieldFatherPhoneResAc = fieldFatherPhoneResAc;
    }

    public String getFieldFatherPhoneRes() {
        return fieldFatherPhoneRes;
    }

    public void setFieldFatherPhoneRes(String fieldFatherPhoneRes) {
        this.fieldFatherPhoneRes = fieldFatherPhoneRes;
    }

    public String getFieldFatherPhoneBusAc() {
        return fieldFatherPhoneBusAc;
    }

    public void setFieldFatherPhoneBusAc(String fieldFatherPhoneBusAc) {
        this.fieldFatherPhoneBusAc = fieldFatherPhoneBusAc;
    }

    public String getFieldFatherPhoneBus() {
        return fieldFatherPhoneBus;
    }

    public void setFieldFatherPhoneBus(String fieldFatherPhoneBus) {
        this.fieldFatherPhoneBus = fieldFatherPhoneBus;
    }

    public String getFieldFatherPhoneMesAc() {
        return fieldFatherPhoneMesAc;
    }

    public void setFieldFatherPhoneMesAc(String fieldFatherPhoneMesAc) {
        this.fieldFatherPhoneMesAc = fieldFatherPhoneMesAc;
    }

    public String getFieldFatherPhoneMes() {
        return fieldFatherPhoneMes;
    }

    public void setFieldFatherPhoneMes(String fieldFatherPhoneMes) {
        this.fieldFatherPhoneMes = fieldFatherPhoneMes;
    }

    public String getFieldMotherName() {
        return fieldMotherName;
    }

    public void setFieldMotherName(String fieldMotherName) {
        this.fieldMotherName = fieldMotherName;
    }

    public String getFieldMotherAddress() {
        return fieldMotherAddress;
    }

    public void setFieldMotherAddress(String fieldMotherAddress) {
        this.fieldMotherAddress = fieldMotherAddress;
    }

    public String getFieldMotherCity() {
        return fieldMotherCity;
    }

    public void setFieldMotherCity(String fieldMotherCity) {
        this.fieldMotherCity = fieldMotherCity;
    }

    public String getFieldMotherCounty() {
        return fieldMotherCounty;
    }

    public void setFieldMotherCounty(String fieldMotherCounty) {
        this.fieldMotherCounty = fieldMotherCounty;
    }

    public String getFieldMotherState() {
        return fieldMotherState;
    }

    public void setFieldMotherState(String fieldMotherState) {
        this.fieldMotherState = fieldMotherState;
    }

    public String getFieldMotherZip() {
        return fieldMotherZip;
    }

    public void setFieldMotherZip(String fieldMotherZip) {
        this.fieldMotherZip = fieldMotherZip;
    }

    public String getFieldMotherPhoneResAc() {
        return fieldMotherPhoneResAc;
    }

    public void setFieldMotherPhoneResAc(String fieldMotherPhoneResAc) {
        this.fieldMotherPhoneResAc = fieldMotherPhoneResAc;
    }

    public String getFieldMotherPhoneRes() {
        return fieldMotherPhoneRes;
    }

    public void setFieldMotherPhoneRes(String fieldMotherPhoneRes) {
        this.fieldMotherPhoneRes = fieldMotherPhoneRes;
    }

    public String getFieldMotherPhoneBusAc() {
        return fieldMotherPhoneBusAc;
    }

    public void setFieldMotherPhoneBusAc(String fieldMotherPhoneBusAc) {
        this.fieldMotherPhoneBusAc = fieldMotherPhoneBusAc;
    }

    public String getFieldMotherPhoneBus() {
        return fieldMotherPhoneBus;
    }

    public void setFieldMotherPhoneBus(String fieldMotherPhoneBus) {
        this.fieldMotherPhoneBus = fieldMotherPhoneBus;
    }

    public String getFieldMotherPhoneMesAc() {
        return fieldMotherPhoneMesAc;
    }

    public void setFieldMotherPhoneMesAc(String fieldMotherPhoneMesAc) {
        this.fieldMotherPhoneMesAc = fieldMotherPhoneMesAc;
    }

    public String getFieldMotherPhoneMes() {
        return fieldMotherPhoneMes;
    }

    public void setFieldMotherPhoneMes(String fieldMotherPhoneMes) {
        this.fieldMotherPhoneMes = fieldMotherPhoneMes;
    }

    public String getGuardianType() {
        return guardianType;
    }

    public void setGuardianType(String guardianType) {
        this.guardianType = guardianType;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianAddress() {
        return guardianAddress;
    }

    public void setGuardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
    }

    public String getGuardianCity() {
        return guardianCity;
    }

    public void setGuardianCity(String guardianCity) {
        this.guardianCity = guardianCity;
    }

    public String getGuardianState() {
        return guardianState;
    }

    public void setGuardianState(String guardianState) {
        this.guardianState = guardianState;
    }

    public String getGuardianZip() {
        return guardianZip;
    }

    public void setGuardianZip(String guardianZip) {
        this.guardianZip = guardianZip;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }
}

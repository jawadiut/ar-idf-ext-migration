package net.therap.model.ar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jawad
 * @since 5/28/14  3:42 PM
 */
@Entity
@Table(name = "TEMP_AR2_DDS_IFSP_SERIALIZED")
public class DdsIfspSerialized {
    @Id
    @Column(name = "rec_id")
    private Long recId;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "type")
    private String type;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

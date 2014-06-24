package net.therap.model.ar;

import javax.persistence.*;

/**
 * @author jawad
 * @since 5/28/14  3:39 PM
 */
@Entity
@Table(name = "TEMP_AR2_EI_CLIENT")
public class ActiveEiClient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arEiSeq")
    @SequenceGenerator(name = "arEiSeq", sequenceName = "ar_ei_seq")
    private long id;
    @Column(name = "client_id")
    private long clientId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}


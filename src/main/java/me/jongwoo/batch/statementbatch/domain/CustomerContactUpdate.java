package me.jongwoo.batch.statementbatch.domain;

import org.springframework.util.StringUtils;

public class CustomerContactUpdate extends CustomerUpdate{

    private final String emailAddress;
    private final String homePhone;
    private final String cellPhone;
    private final String workPhone;
    private final Integer notificationPreferences;

    public CustomerContactUpdate(long customerId, String emailAddress, String homePhone, String cellPhone, String workPhone, Integer notificationPreferences) {
        super(customerId);
        this.emailAddress = StringUtils.hasText(emailAddress) ? emailAddress : null;
        this.homePhone = StringUtils.hasText(homePhone) ? homePhone : null;
        this.cellPhone = StringUtils.hasText(cellPhone) ? cellPhone : null;
        this.workPhone = StringUtils.hasText(workPhone) ? workPhone : null;
        this.notificationPreferences = notificationPreferences;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public Integer getNotificationPreferences() {
        return notificationPreferences;
    }

    @Override
    public String toString() {
        return "CustomerContactUpdate{" +
                "emailAddress='" + emailAddress + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", notificationPreferences=" + notificationPreferences +
                ", customerId=" + customerId +
                '}';
    }
}

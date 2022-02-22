package me.jongwoo.batch.statementbatch.domain;

public class CustomerContactUpdate extends CustomerUpdate{

    private final String emailAddress;
    private final String homePhone;
    private final String cellPhone;
    private final String workPhone;
    private final Integer notificationPreferences;

    public CustomerContactUpdate(long customerId, String emailAddress, String homePhone, String cellPhone, String workPhone, Integer notificationPreferences) {
        super(customerId);
        this.emailAddress = emailAddress;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.workPhone = workPhone;
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

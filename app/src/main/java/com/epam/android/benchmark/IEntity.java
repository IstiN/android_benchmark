package com.epam.android.benchmark;

/**
 * Created by uladzimir_klyshevich on 7/17/15.
 */
public interface IEntity {

    String getId();

    Integer getIndex();

    Boolean isActive();

    String getPicture();

    String getEmployeeName();

    String getEmployeeCompany();

    String getEmployeeEmail();

    String getEmployeeAbout();

    /**
     * Returns formatted date by pattern yyyy-MM-dd
     * @return formatted date
     */
    String getEmployeeRegisteredFormatted();

    Double getLatitude();

    Double getLongitude();

    /**
     * Returns tags joined by ", "
     * @return joined tags, example "tag1, tag2, tag3"
     */
    String getTags();
}

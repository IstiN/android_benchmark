package by.istin.android.xcore.benchmark.assist;

import android.database.Cursor;

import com.epam.benchmark.IEntity;

import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.model.CursorModel;
import by.istin.android.xcore.utils.Log;
import by.istin.android.xcore.utils.StringUtil;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class EntityCursorModel extends CursorModel implements IEntity {

    public EntityCursorModel(Cursor cursor) {
        super(cursor);
    }

    public EntityCursorModel(Cursor cursor, boolean isMoveToFirst) {
        super(cursor, isMoveToFirst);
    }

    @Override
    public String getId() {
        return getString(SourceEntity.ID_AS_STRING);
    }

    @Override
    public Integer getIndex() {
        return getInt(SourceEntity.INDEX);
    }

    @Override
    public Boolean isActive() {
        return getInt(SourceEntity.IS_ACTIVE) == 1;
    }

    @Override
    public String getPicture() {
        return getString(SourceEntity.PICTURE);
    }

    @Override
    public String getEmployeeName() {
        return getString(SourceEntity.NAME);
    }

    @Override
    public String getEmployeeCompany() {
        return getString(SourceEntity.COMPANY);
    }

    @Override
    public String getEmployeeEmail() {
        return getString(SourceEntity.EMAIL);
    }

    @Override
    public String getEmployeeAbout() {
        return getString(SourceEntity.ABOUT);
    }

    @Override
    public String getEmployeeRegisteredFormatted() {
        return getString(SourceEntity.REGISTERED);
    }

    @Override
    public Double getLatitude() {
        return getDouble(SourceEntity.LATITUDE);
    }

    @Override
    public Double getLongitude() {
        return getDouble(SourceEntity.LONGITUDE);
    }

    @Override
    public String getTags() {
        return getString(SourceEntity.TAGS);
    }

    @Override
    public void print() {
        Log.xd(this, StringUtil.joinAll(
                "|",
                getId(),
                getIndex(),
                isActive(),
                getPicture(),
                getEmployeeName(),
                getEmployeeCompany(),
                getEmployeeEmail(),
                getEmployeeAbout(),
                getEmployeeRegisteredFormatted(),
                getLatitude(),
                getLongitude(),
                getTags()));
    }
}

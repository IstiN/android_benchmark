package by.istin.android.xcore.benchmark;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IStorage;
import com.epam.benchmark.util.CloseableList;

import java.util.List;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.provider.IDBContentProviderSupport;

/**
 * Created by uladzimir_klyshevich on 10/9/15.
 */
public class XcoreStorage implements IStorage {

    private XcoreMember mXcoreMember;

    @Override
    public void init(Context context) {
        mXcoreMember = new XcoreMember();
        mXcoreMember.onApplicationCreate((Application)context);
    }

    @Override
    public void save(Context context, List<IEntity> entities) {
        IDBContentProviderSupport contentProvider = XCoreHelper.get(context).getContentProvider();
        final IDBConnection writableConnection = contentProvider.getDbSupport().getConnector().getWritableConnection();
        try {
            writableConnection.beginTransaction();
            ContentValues values = new ContentValues();
            final String tableName = DBHelper.getTableName(SourceEntity.class);
            for (int i = 0; i < entities.size(); i++) {
                final IEntity entity = entities.get(i);
                values.clear();
                values.put(SourceEntity.ID, i);
                values.put(SourceEntity.ID_AS_STRING, entity.getId());
                values.put(SourceEntity.INDEX, entity.getIndex());
                values.put(SourceEntity.IS_ACTIVE, entity.isActive() ? 1 : 0);
                values.put(SourceEntity.PICTURE, entity.getPicture());
                values.put(SourceEntity.NAME, entity.getEmployeeName());
                values.put(SourceEntity.COMPANY, entity.getEmployeeCompany());
                values.put(SourceEntity.EMAIL, entity.getEmployeeEmail());
                values.put(SourceEntity.ABOUT, entity.getEmployeeAbout());
                values.put(SourceEntity.REGISTERED, entity.getEmployeeRegisteredFormatted());
                values.put(SourceEntity.LATITUDE, entity.getLatitude());
                values.put(SourceEntity.LONGITUDE, entity.getLongitude());
                values.put(SourceEntity.TAGS, entity.getTags());
                writableConnection.insertOrReplace(tableName, values);
            }
            writableConnection.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            Log.e("SQLiteStorage", "Cannot insert entities", e);
        } finally {
            writableConnection.endTransaction();
        }
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context) {
        return mXcoreMember.getCachedEntities(context);
    }

    @Override
    public CloseableList<IEntity> getEntities(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        return mXcoreMember.getCachedEntitiesWithFilter(context, isActive, employeeName, startIndex, endIndex);
    }

    @Override
    public void clear(Context context) {
        mXcoreMember.delete(context);
    }

    @Override
    public void clearResources(Context context) {
        mXcoreMember = null;
    }
}

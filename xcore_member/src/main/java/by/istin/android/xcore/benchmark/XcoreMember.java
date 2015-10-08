package by.istin.android.xcore.benchmark;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IMember;
import com.epam.benchmark.util.CloseableList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.benchmark.assist.EntityCursorModel;
import by.istin.android.xcore.benchmark.assist.ResultWrapper;
import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.benchmark.processor.SourceProcessor;
import by.istin.android.xcore.processor.IProcessor;
import by.istin.android.xcore.provider.IDBContentProviderSupport;
import by.istin.android.xcore.utils.AppUtils;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class XcoreMember implements IMember {

    public static final String XCORE = "xcore";

    public static List<Class<? extends XCoreHelper.Module>> MODULES;

    static {
        MODULES = new ArrayList<>();
        MODULES.add(BenchmarkModule.class);
    }

    private XCoreHelper mXCoreHelper;

    public XcoreMember() {
        this.mXCoreHelper = XCoreHelper.get();
    }

    @Override
    public String getName() {
        return XCORE;
    }

    @Override
    public void onApplicationCreate(Application application) {
        this.mXCoreHelper.onCreate(application, MODULES, null);
    }

    @Override
    public Object getSystemService(String name) {
        return mXCoreHelper.getSystemService(name);
    }

    @Override
    public void onActivityCreate(Context context) {

    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }

    @Override
    public void processSmallData(Context context, InputStream inputStream) throws Exception {
        processLargeData(context, inputStream);
    }

    @Override
    public void processLargeData(Context context, InputStream inputStream) throws Exception {
        IProcessor sourceProcessor = AppUtils.get(context, SourceProcessor.APP_SERVICE_KEY);
        sourceProcessor.execute(null, null, inputStream);
    }

    @Override
    public CloseableList<IEntity> getCachedEntities(Context context) {
        IDBContentProviderSupport contentProvider = XCoreHelper.get(context).getContentProvider();
        Cursor query = contentProvider.getDbSupport().query(SourceEntity.class.getName(), null, null, null, null, null, null, null);
        return new ResultWrapper(new EntityCursorModel(query));
    }

    @Override
    public CloseableList<IEntity> getCachedEntitiesWithFilter(Context context, Boolean isActive, String employeeName, Integer startIndex, Integer endIndex) {
        IDBContentProviderSupport contentProvider = XCoreHelper.get(context).getContentProvider();
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        if (isActive != null) {
            stringBuilder.append(SourceEntity.IS_ACTIVE + " = ?");
            params.add(isActive ? "1" : "0");
        }
        if (employeeName != null) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(SourceEntity.NAME + " = ?");
            params.add(employeeName);
        }
        if (startIndex != null) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(SourceEntity.INDEX + " >= ?");
            params.add(String.valueOf(startIndex));
        }
        if (endIndex != null) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(SourceEntity.INDEX + " <= ?");
            params.add(String.valueOf(endIndex));
        }
        String selection = stringBuilder.toString();
        String[] selectionArgs = params.toArray(new String[params.size()]);

        Cursor query = contentProvider.getDbSupport().query(SourceEntity.class.getName(), null, selection, selectionArgs, null, null, null, null);
        return new ResultWrapper(new EntityCursorModel(query));
    }

    @Override
    public void delete(Context context) {
        IDBContentProviderSupport contentProvider = XCoreHelper.get(context).getContentProvider();
        contentProvider.getDbSupport().delete(SourceEntity.class.getName(), null, null);
    }
}

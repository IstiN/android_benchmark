package by.istin.android.xcore.benchmark.processor;

import by.istin.android.xcore.ContextHolder;
import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.benchmark.gson.Response;
import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.processor.impl.AbstractGsonBatchProcessor;
import by.istin.android.xcore.provider.IDBContentProviderSupport;
import by.istin.android.xcore.source.DataSourceRequest;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class SourceProcessor extends AbstractGsonBatchProcessor<Response> {

    public static final String APP_SERVICE_KEY = "source:processor";

    public SourceProcessor(IDBContentProviderSupport contentProviderSupport) {
        super(SourceEntity.class, Response.class, contentProviderSupport);
    }

    @Override
    protected void onStartProcessing(DataSourceRequest dataSourceRequest, IDBConnection dbConnection) {
        super.onStartProcessing(dataSourceRequest, dbConnection);
        dbConnection.delete(DBHelper.getTableName(SourceEntity.class), null, null);
    }

    @Override
    protected void onProcessingFinish(DataSourceRequest dataSourceRequest, Response response) throws Exception {
        super.onProcessingFinish(dataSourceRequest, response);
        notifyChange(ContextHolder.get(), SourceEntity.class);
    }

    @Override
    protected int getListBufferSize() {
        return 0;
    }

    @Override
    public String getAppServiceKey() {
        return APP_SERVICE_KEY;
    }

}

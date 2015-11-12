package by.istin.android.xcore.benchmark;

import android.content.Context;

import by.istin.android.xcore.XCoreHelper;
import by.istin.android.xcore.benchmark.db.SourceEntity;
import by.istin.android.xcore.benchmark.processor.SourceProcessor;
import by.istin.android.xcore.provider.IDBContentProviderSupport;

/**
 * Created by uladzimir_klyshevich on 7/18/15.
 */
public class BenchmarkModule extends XCoreHelper.BaseModule {

    public static final Class<?>[] ENTITIES = new Class[] {
        SourceEntity.class
    };

    @Override
    protected void onCreate(Context context) {
        final IDBContentProviderSupport contentProvider = registerContentProvider(ENTITIES);
        registerAppService(new SourceProcessor(contentProvider));
        //registerAppService(new Source2Processor(contentProvider));
    }

}

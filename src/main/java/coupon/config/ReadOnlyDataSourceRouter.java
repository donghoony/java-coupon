package coupon.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() &&
            RoutingContext.isRoutableToReadSource()) {
            log.info("[ROUTING] Routing to read source");
            return DataSourceType.READER;
        }
        log.info("[ROUTING] Routing to write source");
        return DataSourceType.WRITER;
    }
}

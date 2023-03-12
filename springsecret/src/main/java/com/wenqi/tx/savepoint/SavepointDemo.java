package com.wenqi.tx.savepoint;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

/**
 * savepoint示例:
 * 以银行账户间转账为例，来说明如何使用TransactionStatus创建基于Savepoint的嵌套事务。
 * <p>
 * 现在不是从一个账户转到另一个账户，而是从一个账户转到两个账户，一个是主账户，一个备用账户。
 * 如果向主账户转账失败，则将金额转入备用账户。总之，金额从第一个账户取出之后，必须存入两个
 * 账户的其中一个，以保证整个事务的完整性。
 *
 * @author Wenqi Liang
 * @date 2023/3/12
 */
public class SavepointDemo {
    private static final Logger logger = LoggerFactory.getLogger(SavepointDemo.class);

    public static void main(String[] args) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
                BigDecimal transferAmount = new BigDecimal("20000");
                try {
                    // 取钱
                    withdraw("WITHDRAW_ACCOUNT_ID", transferAmount);
                    // 创建savepoint
                    Object savepointBeforeDeposit = txStatus.createSavepoint();
                    try {
                        // 转钱
                        deposit("MAIN_ACCOUNT_ID", transferAmount);
                    } catch (Exception e) {
                        logger.warn("rollback to savepoint for main account transfer failure ", e);
                        // 回滚事务到Savepoint
                        txStatus.rollbackToSavepoint(savepointBeforeDeposit);
                        // 转钱到备用账户
                        deposit("SECONDARY_ACCOUNT_ID",transferAmount);
                    } finally {
                        // 释放savepoint
                        txStatus.releaseSavepoint(savepointBeforeDeposit);
                    }

                } catch (Exception ex) {
                    logger.warn("failed to complete transfer operation!", ex);
                    txStatus.setRollbackOnly();
                }
            }

            /**
             * 存款
             */
            private void deposit(String mainAccountId, BigDecimal transferAmount) {
            }

            /**
             * 提取
             */
            private void withdraw(String withdrawAccountId, BigDecimal transferAmount) {

            }
        });
    }
}

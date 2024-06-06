package com.superngb.bankingsystem.domain.account;

import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.account.TransferRequestModel;
import org.springframework.stereotype.Component;

@Component
public interface AccountInputBoundary {

    ResponseModel<?> transferMoney(TransferRequestModel transferRequestModel);
}

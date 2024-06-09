package com.superngb.bankingsystem.domain.search;

import com.superngb.bankingsystem.model.FilterRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface SearchInputBoundary {

    ResponseModel<?> filter(FilterRequestModel filterRequestModel, Pageable pageable);
}

package com.likang.domain.response;

import com.likang.domain.Consignation;

import java.util.List;

/**
 * User: likang
 * Date: 18/1/24
 * Time: 下午8:51
 */
public class ConsignationResponse {
    private String status;
    private List<Consignation> consignations;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Consignation> getConsignations() {
        return consignations;
    }

    public void setConsignations(List<Consignation> consignations) {
        this.consignations = consignations;
    }
}

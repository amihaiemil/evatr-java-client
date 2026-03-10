/**
 * Copyright (c) 2026-, Extremely Distributed Technologies SRL
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.amihaiemil.web.evatr;

import com.amihaiemil.web.openapi.evatr.model.BestaetigungsantwortDto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Response Status of a VAT Number.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
final class EvatrVatStatusResponse implements EvatrVatStatus {
    private final BestaetigungsantwortDto response;
    private final List<EvatrMessage> messages;

    EvatrVatStatusResponse(final BestaetigungsantwortDto response, final List<EvatrMessage> messages) {
        this.response = response;
        this.messages = messages;
    }

    @Override
    public String technicalId() {
        return this.response.getId();
    }

    @Override
    public String technicalStatusCode() {
        return this.response.getStatus();
    }

    @Override
    public OffsetDateTime callTimeStamp() {
        return this.response.getAnfrageZeitpunkt();
    }

    @Override
    public OffsetDateTime validFrom() {
        return this.response.getGueltigAb();
    }

    @Override
    public OffsetDateTime validTo() {
        return this.response.getGueltigBis();
    }

    @Override
    public String message() {
        for(final EvatrMessage m : this.messages) {
            if(m.statusCode().equals(this.response.getStatus())) {
                return m.message();
            }
        }
        return "";
    }

    @Override
    public String companyName() {
        return this.response.getErgFirmenname() != null ? this.response.getErgFirmenname() : "";
    }

    @Override
    public String companyStreet() {
        return this.response.getErgStrasse() != null ? this.response.getErgStrasse() : "";
    }

    @Override
    public String companyCity() {
        return this.response.getErgOrt() != null ? this.response.getErgOrt() : "";
    }

    @Override
    public String companyZipCode() {
        return this.response.getErgPlz() != null ? this.response.getErgPlz() : "";
    }

    @Override
    public String toString() {
        return "EvatrVatStatusResponse{" +
            "\n technicalId = " + this.technicalId() +
            ",\n technicalStatusCode = " + this.technicalStatusCode() +
            ",\n callTimeStamp = " + this.callTimeStamp() +
            ",\n message = " + this.message() +
            ",\n validFrom = " + this.validFrom() +
            ",\n validTo = " + this.validTo() +
            ",\n companyName = " + this.companyName() +
            ",\n companyStreet = " + this.companyStreet() +
            ",\n companyCity = " + this.companyCity() +
            ",\n companyZipCode = " + this.companyZipCode() +
            "\n}";
    }
}

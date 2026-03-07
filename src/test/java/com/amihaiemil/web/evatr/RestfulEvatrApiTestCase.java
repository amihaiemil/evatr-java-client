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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link RestfulEvatrApi}.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class RestfulEvatrApiTestCase {
    private final List<String> countryCodes = Arrays.asList(
        "AT", "BE", "BG", "CY", "CZ", "DE", "DK", "EE", "EL", "ES", "FI", "FR", "HR", "HU", "IE", "IT", "LT", "LU", "LV", "MT", "NL", "PL", "PT", "RO", "SE", "SI", "SK", "XI"
    );

    @Test
    public void fetchesSupportedCountries() throws IOException {
        final List<EvatrCountry> countries = new RestfulEvatrApi().supportApi().supportedCountries();
        countries.forEach(
            country -> MatcherAssert.assertThat(
                    country.code(), Matchers.isOneOf(countryCodes.toArray()))
        );
    }

    @Test
    public void fetchesEvatrStatusMessages() throws IOException {
        final List<EvatrMessage> messages = new RestfulEvatrApi().supportApi().evatrMessages();
        messages.forEach(
            message -> {
                MatcherAssert.assertThat(
                    message.statusCode(), Matchers.startsWith("evatr-"));
                MatcherAssert.assertThat(
                    message.message(), Matchers.not(Matchers.isEmptyOrNullString()));
            }
        );
    }

    @Test
    public void verifiesVatNumberCorrectly() throws IOException {
        new RestfulEvatrApi().vatApi().verifyExternalVatNumber("DE123456789","ATU12345678");
    }

}

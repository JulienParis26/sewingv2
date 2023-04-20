package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricTypes.class);
        FabricTypes fabricTypes1 = new FabricTypes();
        fabricTypes1.setId(1L);
        FabricTypes fabricTypes2 = new FabricTypes();
        fabricTypes2.setId(fabricTypes1.getId());
        assertThat(fabricTypes1).isEqualTo(fabricTypes2);
        fabricTypes2.setId(2L);
        assertThat(fabricTypes1).isNotEqualTo(fabricTypes2);
        fabricTypes1.setId(null);
        assertThat(fabricTypes1).isNotEqualTo(fabricTypes2);
    }
}

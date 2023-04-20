package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricSellerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricSeller.class);
        FabricSeller fabricSeller1 = new FabricSeller();
        fabricSeller1.setId(1L);
        FabricSeller fabricSeller2 = new FabricSeller();
        fabricSeller2.setId(fabricSeller1.getId());
        assertThat(fabricSeller1).isEqualTo(fabricSeller2);
        fabricSeller2.setId(2L);
        assertThat(fabricSeller1).isNotEqualTo(fabricSeller2);
        fabricSeller1.setId(null);
        assertThat(fabricSeller1).isNotEqualTo(fabricSeller2);
    }
}

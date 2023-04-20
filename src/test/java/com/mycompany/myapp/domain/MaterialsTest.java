package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaterialsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materials.class);
        Materials materials1 = new Materials();
        materials1.setId(1L);
        Materials materials2 = new Materials();
        materials2.setId(materials1.getId());
        assertThat(materials1).isEqualTo(materials2);
        materials2.setId(2L);
        assertThat(materials1).isNotEqualTo(materials2);
        materials1.setId(null);
        assertThat(materials1).isNotEqualTo(materials2);
    }
}

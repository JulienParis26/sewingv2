package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fabric.class);
        Fabric fabric1 = new Fabric();
        fabric1.setId(1L);
        Fabric fabric2 = new Fabric();
        fabric2.setId(fabric1.getId());
        assertThat(fabric1).isEqualTo(fabric2);
        fabric2.setId(2L);
        assertThat(fabric1).isNotEqualTo(fabric2);
        fabric1.setId(null);
        assertThat(fabric1).isNotEqualTo(fabric2);
    }
}

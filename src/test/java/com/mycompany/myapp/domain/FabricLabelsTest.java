package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricLabelsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricLabels.class);
        FabricLabels fabricLabels1 = new FabricLabels();
        fabricLabels1.setId(1L);
        FabricLabels fabricLabels2 = new FabricLabels();
        fabricLabels2.setId(fabricLabels1.getId());
        assertThat(fabricLabels1).isEqualTo(fabricLabels2);
        fabricLabels2.setId(2L);
        assertThat(fabricLabels1).isNotEqualTo(fabricLabels2);
        fabricLabels1.setId(null);
        assertThat(fabricLabels1).isNotEqualTo(fabricLabels2);
    }
}

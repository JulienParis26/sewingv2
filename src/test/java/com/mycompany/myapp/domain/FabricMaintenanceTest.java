package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricMaintenanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricMaintenance.class);
        FabricMaintenance fabricMaintenance1 = new FabricMaintenance();
        fabricMaintenance1.setId(1L);
        FabricMaintenance fabricMaintenance2 = new FabricMaintenance();
        fabricMaintenance2.setId(fabricMaintenance1.getId());
        assertThat(fabricMaintenance1).isEqualTo(fabricMaintenance2);
        fabricMaintenance2.setId(2L);
        assertThat(fabricMaintenance1).isNotEqualTo(fabricMaintenance2);
        fabricMaintenance1.setId(null);
        assertThat(fabricMaintenance1).isNotEqualTo(fabricMaintenance2);
    }
}

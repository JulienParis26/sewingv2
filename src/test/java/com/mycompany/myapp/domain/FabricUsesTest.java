package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricUsesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricUses.class);
        FabricUses fabricUses1 = new FabricUses();
        fabricUses1.setId(1L);
        FabricUses fabricUses2 = new FabricUses();
        fabricUses2.setId(fabricUses1.getId());
        assertThat(fabricUses1).isEqualTo(fabricUses2);
        fabricUses2.setId(2L);
        assertThat(fabricUses1).isNotEqualTo(fabricUses2);
        fabricUses1.setId(null);
        assertThat(fabricUses1).isNotEqualTo(fabricUses2);
    }
}

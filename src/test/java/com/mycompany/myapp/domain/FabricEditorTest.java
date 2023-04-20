package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FabricEditorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FabricEditor.class);
        FabricEditor fabricEditor1 = new FabricEditor();
        fabricEditor1.setId(1L);
        FabricEditor fabricEditor2 = new FabricEditor();
        fabricEditor2.setId(fabricEditor1.getId());
        assertThat(fabricEditor1).isEqualTo(fabricEditor2);
        fabricEditor2.setId(2L);
        assertThat(fabricEditor1).isNotEqualTo(fabricEditor2);
        fabricEditor1.setId(null);
        assertThat(fabricEditor1).isNotEqualTo(fabricEditor2);
    }
}

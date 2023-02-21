package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lims.api.integration.model.ELNRequestForCtReport;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Base64;
import java.util.List;

@Getter
@Setter
public class ELNCtReportVO {

    private File file;
    private List<Matnr> matnr;

    public static ELNCtReportVO of(ELNRequestForCtReport vo) {
        return new ModelMapper().map(vo, ELNCtReportVO.class);
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class File extends RevStateful implements KeyGenerator {
        @MappingKey
        private String ctId;
        @MappingKey
        private String ctSeq;
        private String fileName;
        private String data;

        @Setter(AccessLevel.NONE)
        private byte[] fileData;

        public void decodedFileData() {
            this.fileData = Base64.getDecoder().decode(this.data);
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Matnr extends RevStateful implements KeyGenerator {
        @MappingKey
        private String ctId;
        @MappingKey
        private String ctSeq;
        @MappingKey
        private String labNoMatnr;
    }

}
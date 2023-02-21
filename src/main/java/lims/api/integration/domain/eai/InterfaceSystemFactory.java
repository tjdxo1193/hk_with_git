package lims.api.integration.domain.eai;

import lims.api.integration.enums.InterfaceSystemType;
import lims.api.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @implNote
 *  LIMS와 QMS의 연계 시스템 코드를 동일하게 가져가기 위해 Spring MVC에 호환되도록 구성합니다.
 */
@Slf4j
@Configuration
@PropertySource("classpath:interface-system/${spring.profiles.active}.properties")
public class InterfaceSystemFactory {

    @Value("${interface.eai.protocol}")
    private String EAI_PROTOCOL;

    @Value("${interface.eai.host}")
    private String EAI_HOST;


    @Value("${interface.eln.protocol:@null}")
    private String ELN_PROTOCOL;

    @Value("${interface.eln.host:@null}")
    private String ELN_HOST;


    @Value("${interface.mes.protocol:@null}")
    private String MES_PROTOCOL;

    @Value("${interface.mes.host:@null}")
    private String MES_HOST;


    @Value("${interface.qms.protocol:@null}")
    private String QMS_PROTOCOL;

    @Value("${interface.qms.host:@null}")
    private String QMS_HOST;


    @Value("${interface.sap.protocol:@null}")
    private String SAP_PROTOCOL;

    @Value("${interface.sap.host:@null}")
    private String SAP_HOST;


    @Value("${interface.srm.protocol:@null}")
    private String SRM_PROTOCOL;

    @Value("${interface.srm.host:@null}")
    private String SRM_HOST;

    public InterfaceSystem createSystem(InterfaceSystemType type) {
        if (type == InterfaceSystemType.ELN) {
            return new ELNSystem(createSystemProperty(ELN_PROTOCOL, ELN_HOST));
        }
        if (type == InterfaceSystemType.MES) {
            return new MESSystem(createSystemProperty(MES_PROTOCOL, MES_HOST));
        }
        if (type == InterfaceSystemType.QMS) {
            return new QMSSystem(createSystemProperty(QMS_PROTOCOL, QMS_HOST));
        }
        if (type == InterfaceSystemType.SAP) {
            return new SAPSystem(createSystemProperty(SAP_PROTOCOL, SAP_HOST));
        }
        if (type == InterfaceSystemType.SRM) {
            return new SRMSystem(createSystemProperty(SRM_PROTOCOL, SRM_HOST));
        }

        log.error("[{}] There is no matched interface system. {}", ThreadUtil.getCurrentMethodName(), type.name());

        return null;
    }

    private InterfaceSystemProperty createSystemProperty(String protocol, String host) {
        return new InterfaceSystemProperty(EAI_PROTOCOL, EAI_HOST, protocol, host);
    }

}
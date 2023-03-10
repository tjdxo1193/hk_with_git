package lims.api.sy.service.impl;

import io.jsonwebtoken.lang.Strings;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.enums.TrsInterface;
import lims.api.sy.service.InterfaceMasterService;
import lims.api.sy.vo.InterfaceMasterVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class InterfaceMasterServiceImpl implements InterfaceMasterService {

    private final Set<RevInterface> revSet = new HashSet<>(List.of(RevInterface.values()));
    private final Set<TrsInterface> trsSet = new HashSet<>(List.of(TrsInterface.values()));

    @Override
    public List<InterfaceMasterVO> getMasters(InterfaceMasterVO param) {
        List<InterfaceMasterVO> masters = new ArrayList<>();
        revSet.forEach(rev -> masters.add(new InterfaceMasterVO(rev)));
        trsSet.forEach(trs -> masters.add(new InterfaceMasterVO(trs)));

        Predicate<InterfaceMasterVO> idPredicate = containsId(param.getId());
        Predicate<InterfaceMasterVO> sendDirectionPredicate = equalsSendDirection(param.getSendDirection());
        Predicate<InterfaceMasterVO> systemPredicate = equalsSystem(param.getSystem());

        return masters.stream()
                .filter(idPredicate)
                .filter(sendDirectionPredicate)
                .filter(systemPredicate)
                .collect(Collectors.toList());
    }

    private Predicate<InterfaceMasterVO> containsId(String id) {
        return o -> !Strings.hasLength(id) || (o.getId() != null && o.getId().contains(id));
    }

    private Predicate<InterfaceMasterVO> equalsSendDirection(String sendDirection) {
        return o -> !Strings.hasLength(sendDirection) || sendDirection.equals(o.getSendDirection());
    }

    private Predicate<InterfaceMasterVO> equalsSystem(InterfaceSystemType system) {
        return o -> system == null || system.equals(o.getSystem());
    }
}
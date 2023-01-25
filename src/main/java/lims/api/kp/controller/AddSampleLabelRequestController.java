package lims.api.kp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/kp/addSampleLabelRequest")
public class AddSampleLabelRequestController {
}
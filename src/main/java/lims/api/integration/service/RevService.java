package lims.api.integration.service;

import lims.api.integration.enums.RevInterface;

import java.util.function.Consumer;
import java.util.function.Function;

public interface RevService {

    void execute(RevInterface revInterface, Integer degree, Function<Integer, Integer> mainProcess, Consumer<Integer> postProcessorRunnable);

    void execute(RevInterface revInterface, Integer degree, Function<Integer, Integer> mainProcess);

}
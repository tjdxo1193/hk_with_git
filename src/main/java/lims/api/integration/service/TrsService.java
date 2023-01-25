package lims.api.integration.service;

import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.TrsInterface;

import java.util.List;
import java.util.function.Supplier;

public interface TrsService {

    <T extends TrsStateful> void execute(TrsInterface trsInterface, T recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process);

    <T extends TrsStateful> void execute(TrsInterface trsInterface, List<T> recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process);

    <T extends TrsStateful> void executeAsync(TrsInterface trsInterface, List<T> recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process);
}
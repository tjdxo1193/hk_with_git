package lims.api.util.process;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @param <T> KeyGenerator를 구현한 객체입니다.
 *            KeyObject를 반환하는 generateKey 메서드가 존재해야하며
 *            해당 KeyObject에 담긴 데이터를 비교하여 insert 할지 update할 지 결정하기 때문에
 *            generateKey 메서드가 반환하는 KeyObject는 키값을 온전히 가지고 있어야 합니다.
 */
public class SimpleSaveProcess<T extends KeyGenerator> {

    /**
     * 동일한 키를 가진 데이터가 존재하면 update, 없으면 insert하는 로직을 간단하게 작성합니다.
     * 오라클의 MERGE문 같은 역할.
     * 다만 MERGE문은 개발 속도는 조금 빠르지만 디버깅도 불편하고 유지보수성이 떨어지기 떄문에 사용하지 않습니다.
     *
     * @param dataList          저장할 데이터 리스트.
     * @param targetDataList    저장할 테이블에서 가져온 데이터 리스트. (새로 저장되는 데이터와 비교하기 위한 기존 데이터)
     * @param createHandler     insert interface
     * @param updateHandler     update 메서드. 첫 번째 인자로 전달되는 객체(from dataList)에 값이 변경된 필드가 존재하는지 여부를
     *                          기존 데이터(targetDataList)를 기준으로 검색하여 두번째 인자로 전달해줍니다.
     * @param changeDataHandler 반복문 도중 값이 변경된 데이터가 발견될 때마다 실행되는 훅 메서드입니다.
     *                          값의 비교는 KeyGenerator의 equals, hashCode를 이용하여 수행됩니다.
     *                          equals, hashCode의 구현은 ide의 자동 완성 기능을 이용하는 거나 lombok의 @EqualsAndHashCode 어노테이션을 권장합니다.
     */
    public SaveResult forEachSave(List<T> dataList, List<T> targetDataList, Function<T, Integer> createHandler,
                                  BiFunction<T, Boolean, Integer> updateHandler, Consumer<T> changeDataHandler) {
        Map<KeyObject, T> targetData = targetDataList.stream().collect(Collectors.toMap(T::generateKey, t -> t, (oldKey, newkey) -> oldKey));

        int createdCount = 0;
        int updatedCount = 0;

        boolean exists;
        boolean existsChangeField;
        KeyObject key;
        for (T data : dataList) {
            key = data.generateKey();
            exists = targetData.containsKey(key);
            if (exists) {
                existsChangeField = !data.equals(targetData.get(key));
                if (existsChangeField) {
                    executeChangeDataHandler(changeDataHandler, data);
                }
                updatedCount += updateHandler.apply(data, existsChangeField);
            } else {
                executeChangeDataHandler(changeDataHandler, data);
                createdCount += createHandler.apply(data);
            }

            /**
             * TODO
             *  현재는 비교 데이터에는 없고, 새로 저장하는 데이터에는 있는 값에 대한 처리가 없다.
             *  위 같은 상황에서 삭제같은 처리가 필요할 수 있다.
             */
        }

        return SaveResult.builder()
                .createdCount(createdCount)
                .updatedCount(updatedCount)
                .totalCount(createdCount + updatedCount)
                .build();
    }

    private void executeChangeDataHandler(Consumer<T> handler, T data) {
        if (handler == null) {
            return;
        }
        handler.accept(data);
    }

    public SaveResult forEachSave(List<T> dataList, List<T> targetDataList, Function<T, Integer> createHandler,
                                  Function<T, Integer> updateHandler, Consumer<T> changeDataHandler) {
        return forEachSave(dataList, targetDataList, createHandler, (t, existsChangeData) -> updateHandler.apply(t), changeDataHandler);
    }

    public SaveResult forEachSave(List<T> dataList, List<T> targetDataList, Function<T, Integer> createHandler,
                                  BiFunction<T, Boolean, Integer> updateHandler) {
        return forEachSave(dataList, targetDataList, createHandler, updateHandler, null);
    }

    public SaveResult forEachSave(List<T> dataList, List<T> targetDataList, Function<T, Integer> createHandler,
                                  Function<T, Integer> updateHandler) {
        return forEachSave(dataList, targetDataList, createHandler, (t, existsChangeData) -> updateHandler.apply(t), null);
    }

    public SaveResult forEachSave(T data, List<T> targetDataList, Function<T, Integer> createHandler,
                                  Function<T, Integer> updateHandler) {
        return forEachSave(List.of(data), targetDataList, createHandler, (t, existsChangeData) -> updateHandler.apply(t), null);
    }

    public SaveResult forEachSave(List<T> dataList, List<T> targetDataList, Function<T, Integer> createHandler) {
        return forEachSave(dataList, targetDataList, createHandler, (param) -> 0, null);
    }

    public SaveResult forEachSave(T data, List<T> targetDataList, Function<T, Integer> createHandler) {
        return forEachSave(List.of(data), targetDataList, createHandler, (param) -> 0, null);
    }

}
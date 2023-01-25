/**
 * Report Designer를 전역 객체에 등록(webpack.config.dev 파일에서 설정)한 뒤 Vue Plugin으로 삽입합니다.
 * Report Designer는 UMD가 적용되어 있지 않습니다. 따라서, 전역 객체의 Report Designer 인스턴스를 삭제하면 동작하지 않습니다.
 * 전역 변수로 접근은 가능하나, 되도록 Vue Plugin으로서 사용하는 것을 권장합니다.
 */
const m2soft = window?.m2soft;

// delete window.m2soft;

export { m2soft as $m2soft };

export default {
  install(app) {
    app.config.globalProperties.$m2soft = m2soft;
  },
};

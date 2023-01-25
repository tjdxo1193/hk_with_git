const getterType = {
  // general
  IS_LOADING: 'isLoading',
  ALERT: 'alert',
  IS_SHOW_ALERT: 'isShowAlert',

  // auth
  IS_LOGGED_IN: 'isLoggedIn',
  AUTHENTICATION_TOKEN: 'authenticationToken',
  MY_CLAIMS: 'myClaims',

  // eSign
  E_SIGN: 'eSign',
  IS_READY_SET_ESIGN_PARAM: 'isReadySetEsignParam',
  GET_E_SIGN_PARAM: 'getESignParam',

  // menu
  GET_MENU_INFOS: 'getMenuInfos',
  GET_MENU_INFOS_AS_OBJECT: 'getMenuInfos',
};

const mutationType = {
  // general
  TO_WELCOME: 'toWelcome',
  TO_LOGIN: 'toLogin',

  INIT_GLOBAL_STATUS: 'initGlobalStatus',
  START_LOADING: 'startLoading',
  STOP_LOADING: 'stopLoading',

  SHOW_ALERT: 'showAlert',
  HIDE_ALERT: 'hideAlert',

  // auth
  AUTHENTICATE: 'authenticate',
  DE_AUTHENTICATE: 'deauthenticate',

  START_RE_AUTHENTICATE: 'startReAuthenticate',
  END_RE_AUTHENNTICATE: 'endReAuthenticate',

  // eSign
  SHOW_E_SIGN: 'showESign',
  HIDE_E_SIGN: 'hideESIgn',
  READY_SET_ESIGN_PARAM: 'readySetEsignParam',
  END_SET_ESIGN_PARAM: 'endSetEsignParam',

  // menu
  SET_MENU_INFO: 'setMenuInfo',
};

const actionType = {
  // auth
  LOGIN: 'login',
  LOGOUT: 'logout',
  VERIFY_TOKEN: 'verifyAuthentication',
  RE_LOGIN: 'reLogin',

  // menu
  FETCH_MENU_INFO_FROM_SERVER: 'fetchMenuInfoFromServer',
};

export { getterType, mutationType, actionType };

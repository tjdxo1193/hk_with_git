import analysisColumnManage from './an';
import developer from './dev';
import glassManage from './gl';
import instManage from './in';
import addSpecManage from './kp';
import laboratoryEventManage from './lb';
import main from './main';
import masterManage from './ms';
import monitorManage from './mt';
import nonconformityTestPreventRecurrence from './np';
import printManage from './pr';
import reagentManage from './re';
import samplePractice from './sample';
import search from './sc';
import standardManage from './sd';
import specimenManage from './sm';
import stabilityManage from './st';
import systemManage from './sy';
import sampleManage from './tp';
import testManage from './ts';

const menus = [
  ...testManage,
  ...nonconformityTestPreventRecurrence,
  ...monitorManage,
  ...stabilityManage,
  ...sampleManage,
  ...addSpecManage,
  ...laboratoryEventManage,
  ...instManage,
  ...standardManage,
  ...reagentManage,
  ...glassManage,
  ...analysisColumnManage,
  ...search,
  ...masterManage,
  ...systemManage,
  ...printManage,
  ...samplePractice,
  ...developer,
  ...specimenManage,
];

export { menus };

export default [...main, ...menus];

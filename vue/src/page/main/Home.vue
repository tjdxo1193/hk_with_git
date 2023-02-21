<template>
  <article class="home">
    <div class="contents">
      <div class="con01">
        <div class="calendar con_left">
          <div class="cal_top">
            <h2>CALENDAR</h2>
            <p>2022-10-01</p>
            <span></span>
          </div>
          <div class="cal_bot">
            <div class="cal_bot01 cal_bot_1st">
              <div class="cal_title"></div>
              <ul class="cal_list">
                <li><span>완제품</span></li>
                <li><span>충전/타정</span></li>
                <li><span>BULK/BASE</span></li>
                <li><span>원료</span></li>
                <li><span>포장재</span></li>
                <li><span>상품</span></li>
              </ul>
            </div>
            <div class="cal_bot02">
              <div class="cal_title">
                <p><img :src="require('@/assets/images/lims_icon01.png')" />시험의뢰</p>
              </div>
              <ul class="cal_list">
                <li><strong>40</strong>건</li>
                <li><strong>12</strong>건</li>
                <li><strong>5</strong>건</li>
                <li><strong>100</strong>건</li>
                <li><strong>22</strong>건</li>
                <li><strong>57</strong>건</li>
              </ul>
            </div>
            <div class="cal_bot03">
              <div class="cal_title">
                <p><img :src="require('@/assets/images/lims_icon02.png')" />시험완료</p>
              </div>
              <ul class="cal_list">
                <li><strong>40</strong>건</li>
                <li><strong>12</strong>건</li>
                <li><strong>5</strong>건</li>
                <li><strong>100</strong>건</li>
                <li><strong>22</strong>건</li>
                <li><strong>57</strong>건</li>
              </ul>
            </div>
          </div>
        </div>

        <div class="con_right">
          <div class="main_title">
            <h3>업무상황</h3>
          </div>
          <table class="tableWrap02">
            <AUIGrid />
            <!-- <colgroup>
						<col style="width:40%"></col>
						<col style="width:60%"></col>
					</colgroup>
					<thead>
						<th>구분</th>
						<th>팀별</th>
					</thead>
					<tbody>
						<tr>
							<td>시험의뢰</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>시험접수</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>검체채취</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>시험지시</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>결과입력</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>결과검토</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>결과검토</td>
							<td>5건</td>
						</tr>
						<tr>
							<td>결과검토</td>
							<td>5건</td>
						</tr>
					</tbody> -->
          </table>
        </div>
      </div>
      <div class="con02">
        <div class="con_left">
          <div class="main_title">
            <h3>Audit 일정</h3>
            <div class="search">
              <Datepicker twin />
              <ButtonBase>조회</ButtonBase>
            </div>
          </div>
          <table class="tableWrap02">
            <AUIGrid />
            <!-- <colgroup>
						<col style="width:7%"></col>
						<col style="width:17%"></col>
						<col style="width:50%"></col>
						<col style="width:26%"></col>
					</colgroup>
					<thead>
						<th>No</th>
						<th>고객사</th>
						<th>감사목적</th>
						<th>일정</th>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>2</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>3</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>4</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>5</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>6</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>7</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
					</tbody> -->
          </table>
        </div>
        <div class="con_right">
          <div class="main_title">
            <h3>SOP 일정</h3>
            <div class="search">
              <Datepicker twin />
              <ButtonBase>조회</ButtonBase>
            </div>
          </div>
          <table class="tableWrap02">
            <AUIGrid />
            <!-- <colgroup>
						<col style="width:7%"></col>
						<col style="width:17%"></col>
						<col style="width:50%"></col>
						<col style="width:26%"></col>
					</colgroup>
					<thead>
						<th>No</th>
						<th>고객사</th>
						<th>감사목적</th>
						<th>일정</th>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>2</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>3</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>4</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>5</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>6</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
						<tr>
							<td>7</td>
							<td>한국콜마</td>
							<td>테스트입니다.</td>
							<td>2022-09-19 12:00:00</td>
						</tr>
					</tbody> -->
          </table>
        </div>
      </div>
    </div>
  </article>
</template>
<script>
export default {
  name: 'Home',
};
</script>

<style lang="scss" scope>
.home {
  width: 100%;
  height: calc(100% + 58px);
  // position: absolute;
  top: -57px;
  left: $layout-side-width;

  // &__bg {
  //   width: 100%;
  //   height: 100%;
  //   background: url('@/assets/images/main_bg.jpg') no-repeat center;
  //   background-size: cover;
  // }
  & .contents {
    padding: 5px;
  }

  & .search {
    display: flex;
  }

  & .mr10 {
    margin-right: 10px !important;
  }
}

.con01 {
  display: flex;
}
.con01 .con_left {
  width: 68%;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ddd;
  margin-right: 2%;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
}
.con01 .con_right {
  width: 30%;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ddd;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
}
.con01 .calendar {
  padding: 30px 40px;
}
.con01 .calendar .cal_top {
  background: #558df4;
  height: 73px;
  border-radius: 5px;
  display: flex;
  align-items: center;
}
.con01 .calendar .cal_top h2 {
  font-size: 34px;
  color: #fff;
  width: 40%;
  padding-left: 30px;
}
.con01 .calendar .cal_top p {
  font-size: 28px;
  color: #fff;
  margin-right: 5px;
  font-weight: 500;
}
.con01 .calendar .cal_top span {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #276ef1 url(@/assets/images/lims_cal_icon01.png) center no-repeat;
  cursor: pointer;
}
.con01 .calendar .cal_bot {
  background: #f5f5f5;
  margin-top: 30px;
  border-radius: 5px;
  padding: 20px 10px;
}
.con01 .calendar .cal_bot .cal_title {
  width: 18%;
  font-size: 22px;
  color: #242424;
  font-weight: 500;
  display: flex;
  justify-content: center;
}
.con01 .calendar .cal_bot .cal_title p {
  position: relative;
  z-index: 1;
}
.con01 .calendar .cal_bot .cal_title p:before {
  content: '';
  width: 46px;
  height: 46px;
  background: #d0ddf5;
  border-radius: 50%;
  position: absolute;
  left: -8px;
  top: -4px;
  z-index: -1;
  opacity: 0.5;
}
.con01 .calendar .cal_bot .cal_title p img {
  margin-top: -2px;
  margin-right: 15px;
}
.con01 .calendar .cal_bot .cal_bot01 {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.con01 .calendar .cal_bot .cal_bot02 {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.con01 .calendar .cal_bot .cal_bot03 {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.con01 .calendar .cal_bot .cal_list {
  display: flex;
  align-items: center;
  width: 82%;
  height: 60px;
}
.con01 .calendar .cal_bot .cal_list li {
  font-size: 16px;
  width: 16.66%;
  text-align: center;
  color: #242424;
  position: relative;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.con01 .calendar .cal_bot_1st .cal_list li {
  height: 45px;
}
.con01 .calendar .cal_bot_1st .cal_list li:before {
  display: none;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(1) span {
  color: #008fc2;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(2) span {
  color: #f7b421;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(3) span {
  color: #81c725;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(4) span {
  color: #00b3a3;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(5) span {
  color: #008fc2;
}
.con01 .calendar .cal_bot .cal_list li:nth-child(6) span {
  color: #81c725;
}
.con01 .calendar .cal_bot .cal_list li strong {
  font-size: 25px;
  font-weight: 500;
  margin-right: 5px;
}
.con01 .calendar .cal_bot .cal_bot01 li span {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 15px;
  line-height: 1;
  font-size: 20px;
  min-width: 112px;
  text-decoration: underline;
}
.con01 .calendar .cal_bot .cal_list li:before {
  content: '';
  width: 1px;
  height: 16px;
  background: #ccc;
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
}
.con01 .con_right {
  padding: 20px;
}
.con01 .con_right h3 {
  font-size: 17px;
  color: #242424;
  margin-bottom: 5px;
}
.mainTable {
  border-top: 1px solid #000;
  text-align: center;
}
.con02 {
  display: flex;
  margin-top: 2%;
}
.con02 .main_title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 20px 0;
}
.con02 .main_title h3 {
  font-size: 17px;
  color: #242424;
}
.con02 .con_left {
  width: 49%;
  margin-right: 2%;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ddd;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
}
.con02 .con_right {
  width: 49%;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ddd;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
}

@media screen and (max-width: 1280px) {
  .lims_main {
    padding: 20px !important;
  }
  .con01 .calendar {
    padding: 30px 20px;
  }
  .con01 .calendar .cal_top h2 {
    font-size: 30px;
  }
  .con01 .con_right h3 {
    font-size: 16px;
  }
  .con01 .calendar .cal_bot .cal_bot01 li span {
    font-size: 18px;
    padding: 10px;
    letter-spacing: -0.5px;
  }
  .con01 .calendar .cal_bot .cal_title {
    margin-left: 5px;
  }
  .con01 .calendar .cal_bot .cal_title p img {
    width: 20%;
    margin-right: 10px;
  }
  .con01 .calendar .cal_bot .cal_title p:before {
    width: 38px;
    height: 38px;
  }
  .con01 .calendar .cal_bot .cal_title p {
    font-size: 18px;
    font-weight: 600;
  }
  .con01 .calendar .cal_bot {
    padding: 20px 5px;
  }
  .con01 .calendar .cal_bot .cal_list li strong {
    margin-right: 0;
  }
  .con02 .con_right p {
    font-size: 16px;
  }
  .con02 .con_right span:before {
    width: 60px;
    height: 60px;
  }
  .con02 .con_right img {
    max-width: 28%;
    max-height: 70%;
  }
  .con02 .main_title h3 {
    font-size: 16px;
  }
  .tableWrap02 {
    padding: 7px 20px 20px;
  }
  .buttonWrap {
    margin-left: 5px;
  }
}

// TODO DatePicker 컴포넌트로 변경한 뒤 삭제
input[type='date'] {
  position: relative;
  border: 1px solid #ccc;
  padding-left: 35px;
  height: 26px;
  vertical-align: middle;
  background: url('@/assets/images/dateIcon.png') no-repeat left center;
}
input[type='date']::-webkit-clear-button,
input[type='date']::-webkit-inner-spin-button {
  display: none;
}
input[type='date']::-webkit-calendar-picker-indicator {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: transparent;
  color: transparent;
  cursor: pointer;
}
</style>

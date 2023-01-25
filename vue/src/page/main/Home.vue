<template>
  <article class="home">
    <div class="contents">
      <div class="con1">
        <div class="con1_left">
          <div class="con1_top">
            <dl>
              <dt class="con1_title">CALENDAR</dt>
              <dd class="date">
                2022-10-01<span><img :src="require('@/assets/images/lims_cal_icon01.png')" /></span>
              </dd>
              <dd></dd>
            </dl>
          </div>
          <div class="con1_list">
            <ul>
              <li></li>
              <li><span>완제품</span></li>
              <li><span>충전/타정</span></li>
              <li><span>BULK/BASE</span></li>
              <li><span>원료</span></li>
              <li><span>포장재</span></li>
              <li><span>상품</span></li>
            </ul>
            <ul class="test">
              <li>
                <span class="mr10"><img :src="require('@/assets/images/lims_icon01.png')" /></span>
              </li>
              <li><strong>40</strong>건</li>
              <li><strong>12</strong>건</li>
              <li><strong>5</strong>건</li>
              <li><strong>5</strong>건</li>
              <li><strong>10</strong>건</li>
              <li><strong>10</strong>건</li>
            </ul>
            <ul class="test">
              <li>
                <span class="mr10"><img :src="require('@/assets/images/lims_icon02.png')" /></span>
              </li>
              <li><strong>40</strong>건</li>
              <li><strong>12</strong>건</li>
              <li><strong>5</strong>건</li>
              <li><strong>5</strong>건</li>
              <li><strong>10</strong>건</li>
              <li><strong>10</strong>건</li>
            </ul>
          </div>
        </div>
        <div class="con1_right">
          <div class="t_title">
            <h3>업무상황</h3>
          </div>
          <table class="mainTable">
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
      <div class="con2">
        <div class="con2_left">
          <div class="t_title">
            <h3>Audit 일정</h3>
            <div class="search">
              <Datepicker twin />
              <ButtonBase>조회</ButtonBase>
            </div>
          </div>
          <table class="mainTable">
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
        <div class="con2_right">
          <div class="t_title">
            <h3>SOP 일정</h3>
            <div class="search">
              <Datepicker twin />
              <ButtonBase>조회</ButtonBase>
            </div>
          </div>
          <table class="mainTable">
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

.con1 {
  display: flex;
  justify-content: space-between;
}
.con1 .con1_left {
  width: 68%;
  background: #fff;
  border-radius: 5px;
  padding: 30px;
  border: 1px solid #ddd;
}
.con1 .con1_left .con1_title {
  color: #00b3a3;
  font-size: 28px;
  font-weight: 600;
}
.con1 .con1_left .con1_top {
  background: #558df4;
  border-radius: 5px;
}
.con1 .con1_left .con1_top dl {
  display: flex;
  height: 78px;
  align-items: center;
}
.con1 .con1_left .con1_top dl dt {
  width: 33.33%;
  color: rgba(255, 255, 255, 0.7);
  padding-left: 30px;
}
.con1 .con1_left .con1_top dl dd {
  width: 33.33%;
  color: #fff;
  text-align: center;
}
.con1 .con1_left .con1_list {
  background: #f5f5f5;
  border-radius: 5px;
  margin-top: 20px;
  padding: 10px 0;
}
.con1 .con1_left .date {
  font-size: 28px;
  font-weight: 600;
}
.con1 .con1_left .date span {
  cursor: pointer;
}
.con1 .con1_left .date img {
  margin: -5px 0 0 5px;
}
.con1 .con1_left ul {
  display: flex;
  align-items: center;
  height: 68px;
  text-align: center;
  padding: 10px;
}
.con1 .con1_left .con1_list ul:nth-child(1) li span {
  background: #d2def5;
  border-radius: 30px;
  padding: 8px 24px;
}
.con1 .con1_left .con1_list ul:nth-child(1) li:nth-child(4) span {
  padding: 8px 10px !important;
}
.con1 .con1_left .con1_list .test li {
  position: relative;
}
.con1 .con1_left .con1_list .test li:nth-child(n + 3):before {
  content: '';
  width: 1px;
  height: 20px;
  background: #ccc;
  position: absolute;
  left: 0;
  top: 3px;
}
.con1 .con1_left ul li {
  width: 15.4%;
  font-size: 20px;
  color: #00003b;
  line-height: 1;
}
.con1 .con1_left ul li:nth-child(1) {
  width: 20%;
}
.con1 .con1_left ul li strong {
  font-size: 24px;
}
.con1 .con1_right {
  width: 30%;
  background: #fff;
  border-radius: 5px;
  padding: 20px;
  border: 1px solid #ddd;
}
.con1 .con1_right ul {
  display: flex;
  justify-content: space-between;
}
.con1 .con1_right ul li {
  background: #e9f0fe;
  border-radius: 5px;
  width: 146px;
  height: 146px;
  text-align: center;
}
.con1 .con1_right ul li a {
  padding: 20px;
  display: block;
  width: 100%;
  height: 100%;
}
.con1 .con1_right ul li span {
  position: relative;
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 36px;
  margin-top: 20px;
}
.con1 .con1_right ul li span:before {
  content: '';
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: #fff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: -1;
}
.con1 .con1_right ul li span:after {
  content: '';
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 2px dotted #fff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: -1;
}
.con1 .con1_right ul li p {
  margin-top: 28px;
  font-size: 18px;
  color: #242424;
}

.con2 {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}
.con2 .con2_left {
  width: 49%;
  padding-right: 2%;
  background: #fff;
  padding: 30px 20px;
  border-radius: 5px;
  border: 1px solid #ddd;
}
.t_title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.t_title h3 {
  color: #242424;
}
.con2 .con2_right {
  width: 49%;
  padding-left: 2%;
  background: #fff;
  padding: 30px 20px;
  border-radius: 5px;
  border: 1px solid #ddd;
}
.con2 .btn {
  background: #276ef1;
  color: #fff;
  border-radius: 3px;
  vertical-align: middle;
  padding: 0 10px;
  height: 26px;
  line-height: 20px;
}
.con2 .btn:hover {
  background: #0050df;
}
.mainTable {
  border-top: 1px solid #000;
  width: 100%;
  margin-top: 10px;
}
// .mainTable th{background:#f8f8f8; height:32px; color:#555}
// .mainTable th:nth-child(n+2){border-left:1px solid #ddd}
// .mainTable tr{border-bottom:1px solid #ddd; height:32px}
// .mainTable tr td{text-align:center; color:#888}
// .mainTable tr td:nth-child(n+2){border-left:1px solid #ddd}

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

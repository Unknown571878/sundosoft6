const layer_names = [
    { name: '광주 전체', id: 'gwangju_dong' },
    { name: '광주 구', id: 'gwangju_gu' },
    { name: '초등학생', id: 'gwangju_population_10c_2024_10' },
    { name: '중학생', id: 'gwangju_population_10j_2024_10' },
    { name: '고등학생', id: 'gwangju_population_10g_2024_10' },
    { name: '20대', id: 'gwangju_population_20y_2024_10' },
    { name: '30대', id: 'gwangju_population_30y_2024_10' },
    { name: '40대', id: 'gwangju_population_40y_2024_10' },
    { name: '50대', id: 'gwangju_population_50y_2024_10' },
    { name: '60대', id: 'gwangju_population_60y_2024_10' },
    { name: '70대', id: 'gwangju_population_70y_2024_10' },
    { name: '80대', id: 'gwangju_population_80y_2024_10' },
    { name: '90대', id: 'gwangju_population_90y_2024_10' },
    { name: '모든 연령대', id: 'gwangju_population_all_2024_10' },
    { name: '경지정리답', id: 'gwangju_toji_use_1110' },
    { name: '미경지정리답', id: 'gwangju_toji_use_1120' },
    { name: '보통,특수작물', id: 'gwangju_toji_use_1210' },
    { name: '과수원 기타', id: 'gwangju_toji_use_1220' },
    { name: '자연초지', id: 'gwangju_toji_use_2110' },
    { name: '인공초지', id: 'gwangju_toji_use_2120' },
    { name: '침엽수림', id: 'gwangju_toji_use_2210' },
    { name: '활엽수림', id: 'gwangju_toji_use_2220' },
    { name: '혼합수림', id: 'gwangju_toji_use_2230' },
    { name: '골프장', id: 'gwangju_toji_use_2310' },
    { name: '공원묘지', id: 'gwangju_toji_use_2320' },
    { name: '유원지', id: 'gwangju_toji_use_2330' },
    { name: '일반주택지', id: 'gwangju_toji_use_3110' },
    { name: '고층주택지', id: 'gwangju_toji_use_3120' },
    { name: '상업,업무지', id: 'gwangju_toji_use_3130' },
    { name: '나대지 및 인공녹지', id: 'gwangju_toji_use_3140' },
    { name: '도로', id: 'gwangju_toji_use_3210' },
    { name: '철로 및 주변지역', id: 'gwangju_toji_use_3220' },
    { name: '공항', id: 'gwangju_toji_use_3230' },
    { name: '공업시설', id: 'gwangju_toji_use_3310' },
    { name: '공업나지, 기타', id: 'gwangju_toji_use_3320' },
    { name: '발전시설', id: 'gwangju_toji_use_3410' },
    { name: '처리장', id: 'gwangju_toji_use_3420' },
    { name: '교육, 군사시설', id: 'gwangju_toji_use_3430' },
    { name: '공공용지', id: 'gwangju_toji_use_3440' },
    { name: '양어장, 양식장', id: 'gwangju_toji_use_3510' },
    { name: '채광지역', id: 'gwangju_toji_use_3520' },
    { name: '매립지', id: 'gwangju_toji_use_3530' },
    { name: '가축사육시설', id: 'gwangju_toji_use_3550' },
    { name: '하천', id: 'gwangju_toji_use_4210' },
    { name: '호, 소', id: 'gwangju_toji_use_4310' },
    { name: '댐', id: 'gwangju_toji_use_4320' }
]

// 레이어의 인덱스와 체크박스의 ID를 배열로 정의
const layers = [
    { id: 'gwangju_dong', layerIndex: 1 },
    { id: 'gwangju_gu', layerIndex: 2 },
    { id: 'gwangju_population_10c', layerIndex: 3 },
    { id: 'gwangju_population_10j', layerIndex: 4 },
    { id: 'gwangju_population_10g', layerIndex: 5 },
    { id: 'gwangju_population_20y', layerIndex: 6 },
    { id: 'gwangju_population_30y', layerIndex: 7 },
    { id: 'gwangju_population_40y', layerIndex: 8 },
    { id: 'gwangju_population_50y', layerIndex: 9 },
    { id: 'gwangju_population_60y', layerIndex: 10 },
    { id: 'gwangju_population_70y', layerIndex: 11 },
    { id: 'gwangju_population_80y', layerIndex: 12 },
    { id: 'gwangju_population_90y', layerIndex: 13 },
    { id: 'gwangju_population_all', layerIndex: 14 },
    { id: 'gwangju_toji_use_1110', layerIndex: 15 },
    { id: 'gwangju_toji_use_1120', layerIndex: 16 },
    { id: 'gwangju_toji_use_1210', layerIndex: 17 },
    { id: 'gwangju_toji_use_1220', layerIndex: 18 },
    { id: 'gwangju_toji_use_2110', layerIndex: 19 },
    { id: 'gwangju_toji_use_2120', layerIndex: 20 },
    { id: 'gwangju_toji_use_2210', layerIndex: 21 },
    { id: 'gwangju_toji_use_2220', layerIndex: 22 },
    { id: 'gwangju_toji_use_2230', layerIndex: 23 },
    { id: 'gwangju_toji_use_2310', layerIndex: 24 },
    { id: 'gwangju_toji_use_2320', layerIndex: 25 },
    { id: 'gwangju_toji_use_2330', layerIndex: 26 },
    { id: 'gwangju_toji_use_3110', layerIndex: 27 },
    { id: 'gwangju_toji_use_3120', layerIndex: 28 },
    { id: 'gwangju_toji_use_3130', layerIndex: 29 },
    { id: 'gwangju_toji_use_3140', layerIndex: 30 },
    { id: 'gwangju_toji_use_3210', layerIndex: 31 },
    { id: 'gwangju_toji_use_3220', layerIndex: 32 },
    { id: 'gwangju_toji_use_3230', layerIndex: 33 },
    { id: 'gwangju_toji_use_3310', layerIndex: 34 },
    { id: 'gwangju_toji_use_3320', layerIndex: 35 },
    { id: 'gwangju_toji_use_3410', layerIndex: 36 },
    { id: 'gwangju_toji_use_3420', layerIndex: 37 },
    { id: 'gwangju_toji_use_3430', layerIndex: 38 },
    { id: 'gwangju_toji_use_3440', layerIndex: 39 },
    { id: 'gwangju_toji_use_3510', layerIndex: 40 },
    { id: 'gwangju_toji_use_3520', layerIndex: 41 },
    { id: 'gwangju_toji_use_3530', layerIndex: 42 },
    { id: 'gwangju_toji_use_3550', layerIndex: 43 },
    { id: 'gwangju_toji_use_4210', layerIndex: 44 },
    { id: 'gwangju_toji_use_4310', layerIndex: 45 },
    { id: 'gwangju_toji_use_4320', layerIndex: 46 }
];

// 각 체크박스에 대해 이벤트 리스너 추가
layers.forEach(function(layerInfo) {
    var checkbox = document.getElementById(layerInfo.id);
    checkbox.addEventListener('change', function(event) {
        if (event.target.checked) {
            var wmsLayer = map.getLayers().item(layerInfo.layerIndex); // WMS 레이어
            wmsLayer.setVisible(checkbox.checked);  // 체크된 상태에 따라 레이어 표시/숨기기
            // 레이어 우선순위에 추가
            // ul 요소 선택
            const ul = document.getElementById("draggable-checkbox-list");
            // 새로운 li 요소 생성
            const newLi = document.createElement("li");
            newLi.setAttribute("draggable", "true");
            newLi.setAttribute("id", layerInfo.id+'-layer');

            const newBtn = document.createElement("button");
            newBtn.setAttribute("id", layerInfo.id);
            newBtn.setAttribute("class", "cancel-layer");

            // 새로운 img 생성
            const newDragImg = document.createElement("img");
            newDragImg.setAttribute("src", "/images/icons8-메뉴.svg");
            newDragImg.setAttribute("style", "width: 20px; height: 20px");

            // 새로운 label 생성
            const newLabel = document.createElement("label");
            newLabel.setAttribute("for", layerInfo.id);

            var findName = layer_names.find(layer => layer.id === layerInfo.id);
            // li 태그 텍스트
            newLabel.textContent = findName.name;

            // 새로운 img 생성
            const newCancelImg = document.createElement("img");
            newCancelImg.setAttribute("src", "/images/icons8-취소.svg");
            newCancelImg.setAttribute("style", "width: 20px; height: 20px");

            newBtn.appendChild(newCancelImg);

            // li에 checkbox, label, img 추가
            newLi.appendChild(newDragImg);
            newLi.appendChild(newLabel);
            newLi.appendChild(newBtn);

            // ul에 li 추가
            ul.appendChild(newLi);

            // cancel 버튼 클릭 이벤트 추가
            newBtn.addEventListener('click', function() {
                // li 태그 삭제
                newLi.remove();
                // 체크박스 해제
                checkbox.checked = false;
            });
        } else {
            const div_layer = document.querySelector(`#${layerInfo.id}-layer`);
            if (div_layer)
                div_layer.remove();
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    // OpenLayers Map 객체 생성
    var map = new ol.Map({
        target: 'map', // map div에 지도 표시
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()  // OpenStreetMap 배경 지도
            }), // 0번
            createWmsLayer('ne:gwangju_dong', false), // 1번 광주 전체
            createWmsLayer('ne:gwangju_gu', false), // 2번 광주 구
            createWmsLayer('ne:gwangju_population_10c_2024_10', false), // 3번 초등학생
            createWmsLayer('ne:gwangju_population_10j_2024_10', false), // 4번 중학생
            createWmsLayer('ne:gwangju_population_10g_2024_10', false), // 5번 고등학생
            createWmsLayer('ne:gwangju_population_20y_2024_10', false), // 6번 20대
            createWmsLayer('ne:gwangju_population_30y_2024_10', false), // 7번 30대
            createWmsLayer('ne:gwangju_population_40y_2024_10', false), // 8번 40대
            createWmsLayer('ne:gwangju_population_50y_2024_10', false), // 9번 50대
            createWmsLayer('ne:gwangju_population_60y_2024_10', false), // 10번 60대
            createWmsLayer('ne:gwangju_population_70y_2024_10', false), // 11번 70대
            createWmsLayer('ne:gwangju_population_80y_2024_10', false), // 12번 80대
            createWmsLayer('ne:gwangju_population_90y_2024_10', false), // 13번 90대
            createWmsLayer('ne:gwangju_population_all_2024_10', false), // 14번 모든 연령대
            createWmsLayer('ne:gwangju_toji_use_1110', false), // 15번 경지정리답
            createWmsLayer('ne:gwangju_toji_use_1120', false), // 16번 미경지정리답
            createWmsLayer('ne:gwangju_toji_use_1210', false), // 17번 보통,특수작물
            createWmsLayer('ne:gwangju_toji_use_1220', false), // 18번 과수원 기타
            createWmsLayer('ne:gwangju_toji_use_2110', false), // 19번 자연초지
            createWmsLayer('ne:gwangju_toji_use_2120', false), // 20번 인공초지
            createWmsLayer('ne:gwangju_toji_use_2210', false), // 21번 침엽수림
            createWmsLayer('ne:gwangju_toji_use_2220', false), // 22번 활엽수림
            createWmsLayer('ne:gwangju_toji_use_2230', false), // 23번 혼합수림
            createWmsLayer('ne:gwangju_toji_use_2310', false), // 24번 골프장
            createWmsLayer('ne:gwangju_toji_use_2320', false), // 25번 공원묘지
            createWmsLayer('ne:gwangju_toji_use_2330', false), // 26번 유원지
            createWmsLayer('ne:gwangju_toji_use_3110', false), // 27번 일반주택지
            createWmsLayer('ne:gwangju_toji_use_3120', false), // 28번 고층주택지
            createWmsLayer('ne:gwangju_toji_use_3130', false), // 29번 상업,업무지
            createWmsLayer('ne:gwangju_toji_use_3140', false), // 30번 나대지 및 인공녹지
            createWmsLayer('ne:gwangju_toji_use_3210', false), // 31번 도로
            createWmsLayer('ne:gwangju_toji_use_3220', false), // 32번 철로 및 주변지역
            createWmsLayer('ne:gwangju_toji_use_3230', false), // 33번 공항
            createWmsLayer('ne:gwangju_toji_use_3310', false), // 34번 공업시설
            createWmsLayer('ne:gwangju_toji_use_3320', false), // 35번 공업나지, 기타
            createWmsLayer('ne:gwangju_toji_use_3410', false), // 36번 발전시설
            createWmsLayer('ne:gwangju_toji_use_3420', false), // 37번 처리장
            createWmsLayer('ne:gwangju_toji_use_3430', false), // 38번 교육, 군사시설
            createWmsLayer('ne:gwangju_toji_use_3440', false), // 39번 공공용지
            createWmsLayer('ne:gwangju_toji_use_3510', false), // 40번 양어장, 양식장
            createWmsLayer('ne:gwangju_toji_use_3520', false), // 41번 채광지역
            createWmsLayer('ne:gwangju_toji_use_3530', false), // 42번 매립지
            createWmsLayer('ne:gwangju_toji_use_3550', false), // 43번 가축사육시설
            createWmsLayer('ne:gwangju_toji_use_4210', false), // 44번 하천
            createWmsLayer('ne:gwangju_toji_use_4310', false), // 45번 호, 소
            createWmsLayer('ne:gwangju_toji_use_4320', false) // 46번 댐
        ],
        view: new ol.View({
            projection: 'EPSG:4326',  // 지도 좌표계 설정
            center: [126.8526, 35.1595],  // 광주의 경도, 위도 좌표
            zoom: 11,  // 기본 줌 레벨
            minZoom: 1, // 최소 줌 레벨 설정
            maxZoom: 15, // 최대 줌 레벨 설정
        })
    });

    // WMS 레이어 생성 함수
    function createWmsLayer(layerName, visible) {
        return new ol.layer.Tile({
            source: new ol.source.TileWMS({
                url: 'http://localhost:8080/geoserver/ne/wms',  // GeoServer WMS URL
                params: {
                    'LAYERS': layerName,  // 사용하려는 레이어 이름
                    'TILED': true,
                    'FORMAT': 'image/png',
                    'SRS': 'EPSG:4326'  // 좌표계 설정
                },
                serverType: 'geoserver'
            }),
            visible: visible  // 기본적으로 레이어 가시성 설정
        });
    }
    // 지도 클릭시
    map.on('click', function (event) {
        var coordinate = event.coordinate;
        var view = map.getView();
        var resolution = view.getResolution();
        var projection = view.getProjection();

        // 클릭한 위치 좌표
        var lonLatText = '클릭한 위치의 위도 : ' +  coordinate[1].toFixed(6) + ', 경도 : ' + coordinate[0].toFixed(6); // 위도, 경도
        let coordinateText = document.getElementById("map_position");
        coordinateText.textContent = lonLatText;

        // 클릭한 위치 이름
        var url1 = map.getLayers().item(1).getSource().getGetFeatureInfoUrl(coordinate, resolution, projection, {
            'INFO_FORMAT': 'application/json'
        });
        if (url1) {
            fetch(url1)
                .then(response => response.json())
                .then(data => {
                    const name = data.features[0].properties.name;
                    let text = document.getElementById("map_text");
                    text.textContent = '현재 클릭한 곳은 ' + name + ' 입니다.';
                })
                .catch(error => console.error('Error fetching feature info:', error));
        }
    });
});
// 메뉴 열고 닫기 토글 기능
function toggleMenu() {
    const menu = document.getElementById('menu');
    const menuButton = document.getElementById('menu-button');
    menu.classList.toggle('hidden');
    menuButton.classList.toggle('hidden');
}

// 서브메뉴 열고 닫기 토글 기능
function toggleSubmenu(event, element) {
    event.preventDefault();
    const listItem = element.parentElement;
    const submenu = listItem.querySelector('.submenu');
    if (submenu) {
        submenu.classList.toggle('active');
    }
}
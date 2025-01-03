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
            createWmsLayer('ne:gwangju_population_all_2024_10', false) // 14번 모든 연령대
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

    // 레이어의 인덱스와 체크박스의 ID를 배열로 정의
    var layers = [
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
        { id: 'gwangju_population_all', layerIndex: 14 }
    ];

    // 각 체크박스에 대해 이벤트 리스너 추가
    layers.forEach(function(layerInfo) {
        var checkbox = document.getElementById(layerInfo.id);
        checkbox.addEventListener('change', function() {
            var wmsLayer = map.getLayers().item(layerInfo.layerIndex); // WMS 레이어
            wmsLayer.setVisible(checkbox.checked);  // 체크된 상태에 따라 레이어 표시/숨기기
        });
    });

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

const draggableList = document.getElementById('draggable-checkbox-list'); // 드래그 가능한 리스트 요소 가져오기
const submitButton = document.getElementById('submit-button'); // 제출 버튼 요소 가져오기
const checkLayer = document.getElementById('check-layer'); // 체크 레이어 요소 가져오기
const layerHeader = document.getElementById('layer-header'); // 레이어 헤더 요소 가져오기
let startX, startY, initialLeft, initialTop; // 팝업 드래그 관련 변수 초기화

let draggedItem = null; // 드래그 중인 항목
let placeholder = null; // 플레이스홀더 요소
let isDragging = false; // 드래그 상태 확인

// 드래그 앤 드롭 이벤트 처리
draggableList.addEventListener('dragstart', (e) => {
    if (e.target.tagName === 'LI') { // 드래그가 LI 요소에서 시작될 때만 실행
        draggedItem = e.target; // 드래그 중인 항목 저장
        draggedItem.classList.add('dragging'); // 드래그 중인 항목에 스타일 추가
        placeholder = document.createElement('li'); // 플레이스홀더 생성
        placeholder.className = 'placeholder'; // 플레이스홀더 스타일 클래스 추가
        e.target.parentNode.insertBefore(placeholder, e.target.nextSibling); // 플레이스홀더를 드래그 항목 아래에 추가
    }
});

draggableList.addEventListener('dragend', () => {
    if (draggedItem) { // 드래그 항목이 있을 경우
        draggedItem.classList.remove('dragging'); // 드래그 중 스타일 제거
        if (placeholder) { // 플레이스홀더가 존재할 경우
            placeholder.parentNode.replaceChild(draggedItem, placeholder); // 플레이스홀더를 드래그 항목으로 교체
            placeholder = null; // 플레이스홀더 제거
        }
        draggedItem = null; // 드래그 항목 초기화
    }
});

draggableList.addEventListener('dragover', (e) => {
    e.preventDefault(); // 기본 동작 방지
    const draggingOverItem = e.target.closest('li'); // 드래그 중인 항목 탐지
    if (draggingOverItem && draggingOverItem !== placeholder && draggingOverItem !== draggedItem) { // 유효한 항목일 경우
        const bounding = draggingOverItem.getBoundingClientRect(); // 항목의 위치 정보 가져오기
        const offset = e.clientY - bounding.top; // 마우스 위치 계산
        if (offset > bounding.height / 2) { // 마우스가 항목의 하단에 위치한 경우
            draggingOverItem.after(placeholder); // 플레이스홀더를 항목 뒤로 이동
        } else { // 상단에 위치한 경우
            draggingOverItem.before(placeholder); // 플레이스홀더를 항목 앞으로 이동
        }
    }
});

// 레이어 드래그 이벤트 처리
layerHeader.addEventListener('mousedown', (e) => {
    isDragging = true; // 드래그 상태 활성화
    startX = e.clientX; // 마우스의 초기 X 위치
    startY = e.clientY; // 마우스의 초기 Y 위치
    const rect = checkLayer.getBoundingClientRect(); // 레이어의 위치 정보 가져오기
    initialLeft = rect.left; // 레이어의 초기 X 위치
    initialTop = rect.top; // 레이어의 초기 Y 위치
    document.body.style.userSelect = "none"; // 텍스트 선택 방지
});

layerHeader.addEventListener('mousemove', (e) => {
    if (isDragging) { // 드래그 상태일 경우
        const dx = e.clientX - startX; // 마우스 X 이동 거리
        const dy = e.clientY - startY; // 마우스 Y 이동 거리
        checkLayer.style.left = `${initialLeft + dx}px`; // 레이어의 새로운 X 위치 설정
        checkLayer.style.top = `${initialTop + dy}px`; // 레이어의 새로운 Y 위치 설정
    }
});

layerHeader.addEventListener('mouseup', () => {
    isDragging = false; // 드래그 상태 비활성화
    document.body.style.userSelect = ""; // 텍스트 선택 재활성화
});

// 제출 버튼 클릭 이벤트 처리
submitButton.addEventListener('click', () => {
    const selectedOptions = []; // 선택된 옵션 저장 배열
    draggableList.querySelectorAll('li').forEach((item) => { // 모든 리스트 항목에 대해 반복
        const checkbox = item.querySelector('input[type="checkbox"]'); // 체크박스 가져오기
        if (checkbox.checked) { // 체크된 항목일 경우
            selectedOptions.push(checkbox.id); // 선택된 옵션 ID 추가
        }
    });
    alert(`Selected options: ${selectedOptions.join(', ')}`); // 선택된 옵션 알림 표시
});

const fullscreen = document.getElementById('fullscreenButton');
const map = document.getElementById('map');

fullscreen.addEventListener('click', () => {
    if (map.requestFullscreen) {
        map.requestFullscreen(); // 표준 API
    } else if (map.webkitRequestFullscreen) {
        map.webkitRequestFullscreen(); // Safari
    } else if (map.msRequestFullscreen) {
        map.msRequestFullscreen(); // IE/Edge
    }
});

const layer_invisible = document.getElementById('layer-invisible');
const check_layer = document.getElementById('check-layer');
const layer_cancel = document.getElementById('layer-cancel');

layer_cancel.addEventListener('click', ()=>{
    if (check_layer.style.display === 'block' || check_layer.style.display === '') {
        check_layer.style.display = 'none';
    }
});

layer_invisible.addEventListener('click', () => {
    if (check_layer.style.display === 'block' || check_layer.style.display === '') {
        check_layer.style.display = 'none'; // 안 보이게 설정
    }
    else if (check_layer.style.display === 'none') {
        check_layer.style.display = 'block'; // 보이게 설정

    }
});
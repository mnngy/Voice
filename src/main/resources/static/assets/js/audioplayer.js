let recent_volume = document.querySelector('#volume');
let volume_show = document.querySelector('#volume_show');
let slider = document.querySelector('#duration_slider');
let track = document.createElement('audio');
let audio_path = document.querySelector('#audio_path');
let timer;

let Playing_song = false;
let index_no = 0;
let autoplay = 0;
let All_song = [
    {
        name: "first song",
        path: "music/test.mp3"
        //img: "img/img1.jpg",
        //singer: "1"
    }
];
function load_track(index_no){
    clearInterval(timer);
    reset_slider();

    track.src = audio_path.textContent;//All_song[index_no].path;
    //title.innerHTML = All_song[index_no].name;
    //track_image.src = All_song[index_no].img;
    //artist.innerHTML = All_song[index_no].singer;
    track.load();

    timer = setInterval(range_slider ,1000);
    //total.innerHTML = All_song.length;
    //present.innerHTML = index_no + 1;
}

load_track(index_no);
function reset_slider(){
    slider.value = 0;
}

function play_audio() {
    if(Playing_song == false){
        track.play();
        Playing_song = true;
    }else{
        track.pause();
        Playing_song = false;
    }
}
function volume_change(){
    volume_show.innerHTML = recent_volume.value;
    track.volume = recent_volume.value/100;
}
function change_duration(){
    slider_position = track.duration * (slider.value / 100);
    track.currentTime = slider_position;
}
function range_slider(){
    let position = 0;

    // update slider position
    if(!isNaN(track.duration)){
        position = track.currentTime * (100 / track.duration);
        slider.value =  position;
    }


    // function will run when the song is over
    if(track.ended){
        //play.innerHTML = '<i class="fa fa-play" aria-hidden="true"></i>';
        if(autoplay==1){
            //index_no += 1;
            load_track(index_no);
            play_audio();
        }
    }
}
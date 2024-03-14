const selfView = document.querySelector('video#selfView');
const remoteView = document.querySelector('video#remoteView');
const audioSelect = document.querySelector('select#audioSource');
const videoSelect = document.querySelector('select#videoSource');
    
const signalingChannel = new WebSocket('ws://localhost:2024/IMPROOK_CARE/api/public/video-chat/');
const pc = new RTCPeerConnection();

let isPolite;
let makingOffer = false;
let ignoreOffer = false;

signalingChannel.onmessage = async ({data}) => {
   const {polite, description, candidate} = JSON.parse(data);

   if(polite) {
      isPolite = polite;
   }

   try {
      if(description) {
            const offerCollision = (description.type === 'offer') &&
                                   (makingOffer || pc.signalingState !== 'stable');

            ignoreOffer = offerCollision && !isPolite;
            if(ignoreOffer) {
               return;
            }

            await pc.setRemoteDescription(description);

            if(description.type === 'offer') {
               await pc.setLocalDescription();
               signalingChannel.send(JSON.stringify({description: pc.localDescription}));
            }

         } else if(candidate) {
            try {
               await pc.addIceCandidate(candidate);
            } catch (error) {
               if(!ignoreOffer) {
                  throw error;
               }
            }
         }
   } catch (error) {
      console.log(error);
   }
}

pc.ontrack = ({track}) => {
   console.log('Received remote track(s) ...');
   remoteView.srcObject = new MediaStream();
   remoteView.srcObject.addTrack(track);
}

pc.onnegotiationneeded = async () => {
   console.log('Negotiation is starting ...');
   try {
      makingOffer = true;
      await pc.setLocalDescription();
      signalingChannel.send(JSON.stringify({description: pc.localDescription}));
   } catch (error) {
      console.log(error);
   } finally {
      makingOffer = false;
   }
};

pc.onicecandidate = ({candidate}) => {
   if(candidate) {
      signalingChannel.send(JSON.stringify({candidate: candidate}))
   }
}


async function getDevices() {
     const mediaDevices = await navigator.mediaDevices.enumerateDevices();

     mediaDevices.forEach(device => {
          const option = document.createElement('option');
          option.value = device.deviceId;
          option.text = device.label;

          if (device.kind === 'videoinput') {
              videoSelect.appendChild(option);
          } else if (device.kind === 'audioinput') {
              audioSelect.appendChild(option);
          }
     });
}

async function start() {
    if (window.stream) {
        window.stream.getTracks().forEach(track => track.stop());
    }

    const audioSource = audioSelect.value;
    const videoSource = videoSelect.value;
    const constraints = {
        audio: { deviceId: audioSource },
        video: { deviceId: videoSource }
    }

    const mediaStream = await navigator.mediaDevices.getUserMedia(constraints);
    window.stream = mediaStream;
    mediaStream.getTracks().forEach(track => pc.addTrack(track));
    selfView.srcObject = mediaStream;

};

audioSelect.onchange = start;
videoSelect.onchange = start;

getDevices();
start();
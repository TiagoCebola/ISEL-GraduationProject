let counter = 0;

export async function init() {
  counter = 0;

  try {
    const stream = await navigator.mediaDevices.getUserMedia({
      audio: true,
      video: true,
    });

    const videoTracks = stream.getVideoTracks();
    const track = videoTracks[0];

    const widgets = document.querySelectorAll('webcomponent-api');

    widgets.forEach((widget) => {
      const element = widget.shadowRoot.querySelector('widget-element');

      const widgetWrapper = element.shadowRoot.querySelector('.widget-wrapper');

      if (getComputedStyle(widgetWrapper).border === '5px solid rgb(0, 128, 0)' && counter < widgets.length) {
        alert(`Getting video from: ${track.label}`);
      } else if (getComputedStyle(widgetWrapper).border !== '5px solid rgb(0, 128, 0)' && counter < widgets.length)
        alert('Video service not available');

      counter++;

      const video = element.shadowRoot.getElementById('vid');

      video.style.width = 'auto';
      video.style.height = '405px';
      video.style.maxWidth = '540px';

      if (video !== null) {
        if ('srcObject' in video) {
          video.srcObject = stream;
        } else {
          video.src = URL.createObjectURL(stream);
        }
        video.srcObject = stream;
      }
    });
  } catch (error) {
    alert(`${error.name}`);
  }
}

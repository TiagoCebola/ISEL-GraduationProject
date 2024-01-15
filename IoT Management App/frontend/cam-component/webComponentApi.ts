import {LitElement, html} from 'lit';
import {customElement, property} from 'lit/decorators.js';

@customElement('webcomponent-api')
export class webComponenteAPI extends LitElement {


  @property({ type: Array }) wiki = new Array;
  @property({ type: String }) name = '';
  @property({ type: String }) id = '';
  @property({ type: String }) svgPath = '';
  @property({ type: String }) posterPath = '';

    constructor() {
        super();

        this.wiki = [];
        
        this.addEventListener('ApiData', (e: any) => {
            this._dataFormatCam(e.detail.data)     
        })

        /* MAKE MULTIPLE FETCH BY SEC */
            setInterval(() => {           
                this._getCameraInfo(this.id) 
        }, 1000); 

    }

    /* Subir de filhos para o pais */
    _sendData(data: unknown) {
        this.dispatchEvent(new CustomEvent('ApiData', {
                detail: { data: data },
                bubbles: true,
                composed: true
        }))
    }

    _getCameraInfo(id : string) {
        fetch(`http://localhost:8001/caminfo/${id}`, {
            method: 'GET'
        })
        .then( (response) => {
            if(response.ok) return response.json();
            return Promise.reject(response);
        })
        .then( (data) => {
            this._sendData(data)
        })
        .catch( (error) => { console.warn('Something went wrong', error)})
    }


    _dataFormatCam(data: any) {
        let properties: any[] = [];
        
        properties.push({
            id: data.id,
            resolution: data.resolution,
            zoom: data.zoom,
            sensibility: data.sensibility,
            angle: data.angle,
            status: data.status
        })
       

        this.wiki = properties
    }
    

/*   static styles = [ style ] */

   render() {
    return html`

        <div class="container">
            ${this.getDateTemplate()}
        </div>

    `;
  } 

    getDateTemplate() {

         return html`
           
            ${this.wiki.map( info => 

                 html`
                    <widget-element
                        name=${this.name}
                        svgPath=${this.svgPath}
                        id=${this.id}
                        ?status=${info.status}
                        posterPath = ${this.posterPath}
                        widgetProperties="${info.resolution}, ${info.zoom}, ${info.sensibility}, ${info.angle}"
                    >
                    </widget-element>
 
                 `

             )}
        
        `
    } 

}


declare global {
    interface HTMLElementTagNameMap {
      'webcomponent-api': webComponenteAPI
    }
}
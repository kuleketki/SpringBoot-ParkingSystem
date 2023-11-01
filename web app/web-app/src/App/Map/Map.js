import React, { useState, useEffect } from 'react';
import {
  withGoogleMap,
  withScriptjs,
  GoogleMap,
  Marker,
  InfoWindow,
} from 'react-google-maps';
import * as parkData from './skateboard-parks.json';
import mapStyles from './mapStyles';
import { connect } from 'react-redux';
import { getParkingSpace } from '../../Store/Actions/parkingspace.action.js';

//import * as parkingSpace from ''

function Map(props) {
  console.log(props.parkingspaceList);
  const [currentPosition, setCurrentPosition] = useState({
    lat: 42.3398067,
    lng: -71.0913604,
  });
  // console.log(props.parkingspaceList);
  // console.log(JSON.parse( sessionStorage.getItem("parkingSpaceList")));
  const [selectedPark, setSelectedPark] = useState('');
  const [isListEmpty, setIsListEmpty] = useState(false);

  const onMarkerClick = (evt) => {
    console.log(evt);
  };

  useEffect(() => {
    //   if(props.parkingspaceList.length ==0)
    //   {
    //     setIsListEmpty(true);
    //     if(isListEmpty)
    //     {
    //       setIsListEmpty(false);
    //      // window.location.reload(true);
    //     }
    //     //setIsListEmpty(false);
    //   }
    //   return()=>
    //   {
    //     setIsListEmpty(false);
    //   };
    //  // var name = document.getElementById("inlineFormInput").value;
  }, props.parkingspaceLis);

  //const parkingspacedata = JSON.parse( sessionStorage.getItem("parkingSpaceList"));
  let markers = [
    {
      id: 1,
      latitude: 42.3398067,
      longitude: -71.0913604,
      shelter: 'marker 1',
    },
    {
      id: 2,
      latitude: 42.6874,
      longitude: -71.0913604,
      shelter: 'marker 2',
    },
    {
      id: 3,
      latitude: 43.3398067,
      longitude: -71.0913604,
      shelter: 'marker 3',
    },
  ];
  return (
    <GoogleMap defaultZoom={10} center={currentPosition}>
      {/* 
      <Marker onClick={onMarkerClick} {...props} />

      {props.parkingspaceList.map((post) => (
        <Marker
          key={post.id}
          position={{
            lat: post.latitude,
            lng: post.longitude,
          }}
          onClick={() => {
            setSelectedPark(post);
          }}
        />
      ))}

      {!isListEmpty && <Marker />}



      {selectedPark && (
        <InfoWindow
          position={{
            lat: selectedPark.coordinates[1],
            lng: selectedPark.coordinates[0],
          }}
        >
          <div>
            <h2>{selectedPark.name}</h2>
          </div>
        </InfoWindow>
      )} */}
      <Marker position={currentPosition} />
      {props.parkingspaceList.map((marker) => {
        {
          console.log(`props.parkingspacelist lat ${marker.latitude}`);
          console.log(`props.parkingspacelist longitutde ${marker.longitude}`);
        }
        //const onClick = props.onClick.bind(this, marker)
        return (
          <Marker
            key={marker.id}
            position={{ lat: marker.latitude, lng: marker.longitude }}
          >
            <InfoWindow>
              <div>{marker.name}</div>
            </InfoWindow>
          </Marker>
        );
      })}
    </GoogleMap>
  );
}

function mapStateToProps(state, ownProps) {
  return {
    parkingspaceList: state.parkingspace.parkingspaceList,
  };
}
const mapDispatchToProps = {
  getParkingSpace: getParkingSpace,
};

const ConnectedViewPage = connect(mapStateToProps, mapDispatchToProps)(Map);

const MapWrapped = withScriptjs(withGoogleMap(Map));

export default function App(props) {
  return (
    <div style={{ width: '100vw', height: '100vh' }}>
      <MapWrapped
        {...props}
        googleMapURL={`https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyCjE3B_C2-lcpcw6m7CoGG4Owqd8Hc0Ogs`}
        loadingElement={<div style={{ height: `100%` }} />}
        containerElement={<div style={{ height: `100%` }} />}
        mapElement={<div style={{ height: `100%` }} />}
      />
    </div>
  );
}

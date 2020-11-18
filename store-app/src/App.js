import React from 'react';
import logo from './logo.svg';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import './App.css';
import { Provider } from 'react-redux';
import { ConfigureStore } from './redux/ConfigureStore';
import { BrowserRouter } from 'react-router-dom';
import MainCP from './components/MainCP';

const store = ConfigureStore()

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <div className="App">
          <MainCP/>
        </div>
      </BrowserRouter>
    </Provider>
  )
}

export default App;

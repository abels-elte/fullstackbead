import './App.css';

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Login } from './views/login/Login';
import { Create } from './views/create/Create';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/create" element={<Create/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

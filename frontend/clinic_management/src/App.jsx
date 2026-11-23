import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'
import Authentication from './components/Authentication';
import Office from './components/Office';
import Offices from './components/Offices';


function App() {
  const [count, setCount] = useState(0)

     return (
        <div>
            <Office />

             <hr />

      <Offices />
        </div>
    );

}

export default App

import React from 'react'
import { Image } from 'react-bootstrap'

function HomeF() {
    return(
        <div className="container">
            {/* <div className="col-12 col-lg-9"> */}
                { <Image className="pt-1" src="../assets/images/home.jpg" rounded fluid /> }
            {/* </div> */}
        </div>
    )
}
export default HomeF
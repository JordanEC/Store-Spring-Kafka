import React from 'react'
import { Link } from 'react-router-dom'

function FooterF(){
    return(
        <div className="footer">
            <div className="row justify-content-center">              
                <div className="col-12 col-sm-4 align-self-center">
                    <div className="text-center">
                        <a className="btn btn-social-icon btn-facebook mr-1" target="_blank" href="http://www.facebook.com"><i className="fa fa-facebook"></i></a>
                        <a className="btn btn-social-icon btn-linkedin mr-1" target="_blank" href="https://www.linkedin.com/in/jordan-espinoza/"><i className="fa fa-linkedin"></i></a>
                        <a className="btn btn-social-icon btn-github mr-1" target="_blank" href="http://twitter.com"><i className="fa fa-twitter"></i></a>
                        <a className="btn btn-social-icon btn-github mr-1" target="_blank" href="https://github.com/JordanEC"><i className="fa fa-github"></i></a>
                    </div>
                </div>
            </div>
            <div className="row justify-content-center">
                    <p className="mb-0">© 2020 JordanEC</p>
            </div>
        </div>
    )
}
export default FooterF
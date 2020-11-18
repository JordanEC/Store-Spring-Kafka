import { createStore, combineReducers, applyMiddleware } from 'redux'
import { createForms } from 'react-redux-form'
import thunk from 'redux-thunk'
import logger from 'redux-logger'
import { StoreAppReducers } from './StoreAppReducers'
// import { InitialFeedback } from './forms'

export const ConfigureStore = () => { 
    const store = createStore(
        combineReducers({
            StoreAppReducers: StoreAppReducers
            // ,...createForms({feedback: InitialFeedback})
        }), applyMiddleware(thunk, logger)
    )
    return store
}
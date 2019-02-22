import React, { Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';
import { PERMISSIONS_RECEIVED, PERMISSIONS_ERROR, PERMISSION_UPDATE } from './actiontypes';
import PermissionSelect from './components/PermissionSelect';
import { emptyPermission } from './constants';
import { Header } from './components/bootstrap/Header';
import { Container } from './components/bootstrap/Container';
import { StyledLinkLeft } from './components/bootstrap/StyledLinkLeft';
import {FormRow } from './components/bootstrap/FormRow';
import {FormLabel } from './components/bootstrap/FormLabel';
import {FormField } from './components/bootstrap/FormField';

class PermissionModify extends Component {
    constructor(props) {
        super(props);
        this.state = { ...props };
    }

    componentDidMount() {
        this.props.onPermissions();
    }

    componentWillReceiveProps(props) {
        this.setState({ ...props });
    }

    render () {
        let {
            permissions,
            permissionsMap,
            permission,
            onPermissionsFieldChange,
            onFieldChange,
            onSaveUpdatedPermission,
        } = this.state;

        return (
            <div>
                <StyledLinkLeft to="/authservice/useradmin/permissions">Up to permission adminstration</StyledLinkLeft><br/>
                <Header>
                    <h1>Modify permission information</h1>
                </Header>
                <form onSubmit={ e => { e.preventDefault(); }}>
                    <Container>
                        <FormRow>
                            <FormLabel htmlFor="permissions">Select permission</FormLabel>
                            <FormField>
                                <PermissionSelect id="permissions" className="form-control" permissions={permissions} permissionsMap={permissionsMap} value={permission.permissionname} onPermissionsFieldChange={onPermissionsFieldChange} />
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel htmlFor="permissionname">Permission name</FormLabel>
                            <FormField>
                                <input id="permissionname" className="form-control" type="text" value={permission.permissionname} onChange={(event) => onFieldChange({permissionname: event.target.value}, permission)} />
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel htmlFor="description">Permission description</FormLabel>
                            <FormField>
                                <input id="description" className="form-control" type="text" value={permission.description} onChange={(event) => onFieldChange({description: event.target.value}, permission)} />
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <button className="form-control" onClick={() => onSaveUpdatedPermission(permission)}>Save changes to permission</button>
                        </FormRow>
                    </Container>
                </form>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        permissions: state.permissions,
        permissionsMap: new Map(state.permissions.map(i => [i.permissionname, i])),
        permission: state.permission,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onPermissions: () => {
            axios
                .get('/authservice/useradmin/api/permissions')
                .then(result => dispatch({ type: PERMISSIONS_RECEIVED, payload: result.data }))
                .catch(error => dispatch({ type: PERMISSIONS_ERROR, payload: error }));
        },
        onPermissionsFieldChange: (selectedValue, permissionsMap) => {
            let permission = permissionsMap.get(selectedValue);
            dispatch({ type: PERMISSION_UPDATE, payload: permission });
        },
        onFieldChange: (formValue, originalPermission) => {
            const permission = { ...originalPermission, ...formValue };
            dispatch({ type: PERMISSION_UPDATE, payload: permission });
        },
        onSaveUpdatedPermission: (permission) => {
            axios
                .post('/authservice/useradmin/api/permission/modify', permission)
                .then(result => dispatch({ type: PERMISSIONS_RECEIVED, payload: result.data }))
                .catch(error => dispatch({ type: PERMISSIONS_ERROR, payload: error }));
            dispatch({ type: PERMISSION_UPDATE, payload: { ...emptyPermission } });
        },
    };
};

PermissionModify = connect(mapStateToProps, mapDispatchToProps)(PermissionModify);

export default PermissionModify;

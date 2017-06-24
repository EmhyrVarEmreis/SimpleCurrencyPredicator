function requireAll(requireContext) {
    return requireContext.keys().map(requireContext);
}

requireAll(require.context('./css', true, /\.css$/));
requireAll(require.context('./images', true, /\.png$/));

requireAll(require.context('./app', true, /.module.js$/));
requireAll(require.context('./app', true, /.js$/));
requireAll(require.context('./app', true, /.html$/));


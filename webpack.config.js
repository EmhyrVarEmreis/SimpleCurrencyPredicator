const path = require('path'),
    webpack = require('webpack'),
    HtmlWebpackPlugin = require('html-webpack-plugin'),
    CopyWebpackPlugin = require('copy-webpack-plugin');

function isWebpackDevServer() {
    return process.argv[1] && !!(/webpack-dev-server/.exec(process.argv[1]));
}

module.exports = function () {
    return {
        entry:     {
            vendors:     "./src/main/webapp/vendors.js",
            application: "./src/main/webapp/application.js"
        },
        output:    {
            path:     path.resolve(__dirname, 'target'),
            filename: "[name]_[hash].js"
        },
        module:    {
            rules: [
                {
                    test:    /sources.*\.js$/,
                    exclude: /node_modules/,
                    loader:  'ng-annotate-loader'
                },
                {
                    test:    /src.*\.js$/,
                    exclude: /node_modules/,
                    loaders: ['ng-annotate-loader']
                },
                {
                    test: /\.css$/,
                    use:  [
                        "style-loader",
                        "css-loader"
                    ]
                },
                {
                    test: /\.scss$/,
                    use:  [
                        "style-loader",
                        "css-loader",
                        "sass-loader"
                    ]
                },
                {
                    test:    /\.(ttf|eot|woff(2)?)(\?[a-z0-9=&.]+)?$/,
                    loader:  'file-loader',
                    options: {
                        name: 'font/[name]_[hash].[ext]'
                    }
                },
                {
                    test:    /\.(png|jpg|svg)$/,
                    loader:  'file-loader',
                    options: {
                        name: 'images/[name].[ext]'
                    }
                },
                {
                    test:    /\.html$/,
                    exclude: {test: /index\.html$/},
                    use:     [
                        {
                            loader:  'ngtemplate-loader',
                            options: {relativeTo: path.resolve(__dirname, './src/main/webapp/app')}
                        },
                        {loader: 'html-loader'}
                    ]
                },
                {test: /bootstrap\/dist\/js\/umd\//, loader: 'imports?jQuery=jquery'}
            ]
        },
        plugins:   [
            new HtmlWebpackPlugin({
                template:    "./src/main/webapp/index.html",
                isDevServer: isWebpackDevServer()
            }),
            new webpack.optimize.CommonsChunkPlugin({
                name:      "vendors",
                minChunks: Infinity
            }),
            new webpack.HotModuleReplacementPlugin(),
            new webpack.ProvidePlugin({
                $:               "jquery",
                jQuery:          "jquery",
                "window.jQuery": "jquery"
            }),
            // new webpack.optimize.UglifyJsPlugin({
            //     sourceMap: false
            // }),
            new CopyWebpackPlugin([
                {
                    from: 'api/*',
                    to:   ''
                }
            ])
        ],
        devtool:   'source-map',
        devServer: {
            inline: true,
            hot:    true,
            port:   3002,
            proxy:  {
                "/api": {
                    target:       'http://localhost:8080/',
                    changeOrigin: true
                }
            }
        }
    }
};
